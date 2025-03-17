package com.gows.sdp.client.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.gows.sdp.client.databinding.FragmentOnboardingEnjoyBinding
import com.gows.sdp.client.ui.auth.LoginActivity

class OnboardingEnjoyFragment : Fragment() {
    private var _binding: FragmentOnboardingEnjoyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingEnjoyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = requireActivity().findViewById<ViewPager2>(com.gows.sdp.client.R.id.viewPager)
        binding.buttonStartOrdering.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }
        binding.buttonPrev.setOnClickListener {
            viewPager.currentItem = 2
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}