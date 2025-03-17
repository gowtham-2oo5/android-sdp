package com.gows.sdp.client.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.gows.sdp.client.ui.auth.EmailPasswordLoginFragment
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
            Log.d("GoogleSignIn", "Sign-in result received: resultCode=${result.resultCode}")
            if (result.resultCode == RESULT_OK) {
                val credential =
                    Identity.getSignInClient(this).getSignInCredentialFromIntent(result.data)
                val idToken = credential.googleIdToken
                Log.d("GoogleSignIn", "Credential received: ${credential.id}")
                Log.d("GoogleSignIn", "idToken: $idToken")

                if (idToken != null) {
                    viewModel.signInWithGoogle(idToken)
                } else {
                    Log.e("GoogleSignIn", "Google Sign-In failed: idToken is null")
                    Toast.makeText(this, "Google Sign-In Failed", Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.e("GoogleSignIn", "Google Sign-In failed: resultCode=${result.resultCode}")
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

            val clientId = getString(R.string.default_web_client_id)
            Log.d("GoogleSignIn", "Using Client ID: $clientId")

            Log.d("GoogleSignIn", "Google Sign-In button clicked")
            oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener { result ->
                    Log.d("GoogleSignIn", "Begin sign-in successful")
                    googleSignInLauncher.launch(
                        IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                    )
                }
                .addOnFailureListener {
                    Log.e("GoogleSignIn", "Google Sign-In failed: ${it.message}", it)
                    Toast.makeText(this, "Google Sign-In Failed: ${it.message}", Toast.LENGTH_SHORT)
                        .show()
                }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginResult.observe(this@LoginActivity) { result ->
                    result.fold(
                        onSuccess = {
                            Log.d("GoogleSignIn", "Login Successful: $it")
                            Toast.makeText(
                                this@LoginActivity,
                                "Login Successful",
                                Toast.LENGTH_SHORT
                            ).show()
                            // Navigate to next screen
                        },
                        onFailure = {
                            Log.e("GoogleSignIn", "Login Failed: ${it.message}", it)
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
