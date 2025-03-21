package com.gows.sdp.client.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gows.sdp.client.MainActivity
import com.gows.sdp.client.auth.GoogleLoginUseCase
import com.gows.sdp.client.auth.PhoneLoginUseCase
import com.gows.sdp.client.data.source.FirebaseRemoteAuthDataSource
import com.gows.sdp.client.databinding.FragmentEmailPasswordLoginBinding
import com.gows.sdp.client.ui.view_model.LoginViewModel
import com.gows.sdp.client.ui.view_model.LoginViewModelFactory

class EmailPasswordLoginFragment : Fragment() {
    private var _binding: FragmentEmailPasswordLoginBinding? = null
    private val binding get() = _binding!!
    private val authRepo by lazy {
        com.gows.sdp.client.data.repo.AuthRepo(FirebaseRemoteAuthDataSource())
    }

    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(
            com.gows.sdp.client.auth.LoginWithEmailUseCase(authRepo),
            phoneLoginUseCase = PhoneLoginUseCase(authRepo),
            googleLoginUseCase = GoogleLoginUseCase(authRepo)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmailPasswordLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogin.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.signInWithEmail(email, password) // Call the function correctly
            }
        }


        binding.textViewCreateAccount.setOnClickListener {
            startActivity(Intent(requireContext(), RegisterActivity::class.java))
        }

        Log.d("EmailPasswordLoginFragment", "onViewCreated called")
        // Observe LiveData instead of collect
        viewModel.loginResult.observe(viewLifecycleOwner) { result ->
            Log.d("EmailPasswordLoginFragment", "loginResult observed")
            result.fold(
                onSuccess = {
                    Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                },
                onFailure = {
                    Toast.makeText(requireContext(), "Login Failed: ${it.message}", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
