package com.gows.sdp.client.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.gows.sdp.client.R
import com.gows.sdp.client.auth.GoogleLoginUseCase
import com.gows.sdp.client.auth.LoginWithEmailUseCase
import com.gows.sdp.client.auth.PhoneLoginUseCase
import com.gows.sdp.client.data.repo.AuthRepo
import com.gows.sdp.client.data.source.FirebaseRemoteAuthDataSource
import com.gows.sdp.client.databinding.ActivityLoginBinding
import com.gows.sdp.client.ui.login.EmailPasswordLoginFragment
import com.gows.sdp.client.ui.view_model.LoginViewModel
import com.gows.sdp.client.ui.view_model.LoginViewModelFactory
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var oneTapClient: SignInClient

    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(
            LoginWithEmailUseCase(AuthRepo(FirebaseRemoteAuthDataSource())),
            PhoneLoginUseCase(AuthRepo(FirebaseRemoteAuthDataSource())),
            GoogleLoginUseCase(AuthRepo(FirebaseRemoteAuthDataSource()))
        )
    }

    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val credential =
                    Identity.getSignInClient(this).getSignInCredentialFromIntent(result.data)
                val idToken = credential.googleIdToken
                if (idToken != null) {
                    viewModel.signInWithGoogle(idToken)
                } else {
                    Toast.makeText(this, "Google Sign-In Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configure One Tap Sign-In
        oneTapClient = Identity.getSignInClient(this)
        val signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.default_web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.loginFragmentContainer, EmailPasswordLoginFragment())
            }
        }

        binding.toggleLoginMethod.setOnCheckedChangeListener { _, isChecked ->
            supportFragmentManager.commit {
                replace(
                    R.id.loginFragmentContainer,
                    if (isChecked) PhoneLoginFragment() else EmailPasswordLoginFragment()
                )
            }
        }

        binding.googleSignInButton.setOnClickListener {
            oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener { result ->
                    googleSignInLauncher.launch(
                        IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                    )
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Google Sign-In Failed: ${it.message}", Toast.LENGTH_SHORT)
                        .show()
                }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginResult.observe(this@LoginActivity) { result ->
                    result.fold(
                        onSuccess = {
                            Toast.makeText(
                                this@LoginActivity,
                                "Login Successful",
                                Toast.LENGTH_SHORT
                            ).show()
                            // Navigate to next screen
                        },
                        onFailure = {
                            Toast.makeText(
                                this@LoginActivity,
                                "Login Failed: ${it.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }
        }

    }
}