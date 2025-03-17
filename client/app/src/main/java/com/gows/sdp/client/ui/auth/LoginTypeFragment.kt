package com.gows.sdp.client.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gows.sdp.client.R
import com.gows.sdp.client.databinding.FragmentLoginTypeBinding
import com.gows.sdp.client.ui.auth.EmailPasswordLoginFragment

class LoginTypeFragment : Fragment() {
    private var _binding: FragmentLoginTypeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonEmailPassword.setOnClickListener {
            // Switch to Email/Password fragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, EmailPasswordLoginFragment())
                .addToBackStack(null)
                .commit()
        }
        binding.buttonGoogle.setOnClickListener {
            // Switch to Google fragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, GoogleLoginFragment())
                .addToBackStack(null)
                .commit()
        }
        binding.buttonPhone.setOnClickListener {
            // Switch to Phone fragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, PhoneLoginFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}