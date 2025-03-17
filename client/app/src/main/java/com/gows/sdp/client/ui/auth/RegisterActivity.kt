package com.gows.sdp.client.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.gows.sdp.client.auth.RegisterUseCase
import com.gows.sdp.client.data.repo.AuthRepo
import com.gows.sdp.client.data.source.FirebaseRemoteAuthDataSource
import com.gows.sdp.client.databinding.ActivityRegisterBinding
import com.gows.sdp.client.ui.view_model.RegisterViewModelFactory
import com.gows.sdp.client.ui.view_model.RegisterViewModel
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels {
        RegisterViewModelFactory(
            RegisterUseCase(
                AuthRepo(FirebaseRemoteAuthDataSource())
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCreateAccount.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            viewModel.createAccountWithEmail(email, password)
        }

        binding.buttonBackToLogin.setOnClickListener {
            navigateToLogin()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.createAccountResult.collect { result ->
                    if (result) {
                        Toast.makeText(this@RegisterActivity, "Create account Successful", Toast.LENGTH_SHORT).show()
                        navigateToLogin()
                    } else {
                        Toast.makeText(this@RegisterActivity, "Create account Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
