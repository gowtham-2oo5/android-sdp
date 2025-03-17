package com.gows.sdp.client.ui.auth


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gows.sdp.client.auth.GoogleLoginUseCase
import com.gows.sdp.client.auth.LoginWithEmailUseCase
import com.gows.sdp.client.auth.PhoneLoginUseCase
import com.gows.sdp.client.data.repo.AuthRepo
import com.gows.sdp.client.data.source.FirebaseRemoteAuthDataSource
import com.gows.sdp.client.databinding.FragmentPhoneLoginBinding
import com.gows.sdp.client.ui.view_model.LoginViewModel
import com.gows.sdp.client.ui.view_model.LoginViewModelFactory

class PhoneLoginFragment : Fragment() {

    private var _binding: FragmentPhoneLoginBinding? = null
    private val binding get() = _binding!!
    private val authRepo by lazy {
        com.gows.sdp.client.data.repo.AuthRepo(
            FirebaseRemoteAuthDataSource()
        )
    }
    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(
            phoneLoginUseCase = PhoneLoginUseCase(authRepo),
            loginWithEmailUseCase = LoginWithEmailUseCase(authRepo),
            googleLoginUseCase = GoogleLoginUseCase(authRepo)
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhoneLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.otpContainer.visibility = View.GONE // Initially hide OTP fields

        binding.buttonSendOtp.setOnClickListener {
            val phone = binding.editTextPhoneNumber.text.toString().trim()
            if (phone.isNotEmpty()) {
                sendOtp("+91$phone")
                binding.otpContainer.visibility = View.VISIBLE
            }
        }

        binding.buttonVerifyOtp.setOnClickListener {
            val otp = binding.editTextOtp.text.toString().trim()
            if (otp.isNotEmpty()) {
                verifyOtp(otp)
            }
        }
    }

    private fun sendOtp(phoneNumber: String) {
        authRepo.sendPhoneVerification(phoneNumber, requireActivity()) { success, message ->
            if (success) {
                Toast.makeText(requireContext(), "OTP Sent", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Error: $message", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun verifyOtp(otp: String) {
        authRepo.verifyPhoneCode(otp) { success, message ->
            if (success) {
                Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Error: $message", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}