package com.gows.sdp.client

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gows.sdp.client.databinding.ActivityMainBinding
import com.gows.sdp.client.ui.fragments.OnboardingEnjoyFragment
import com.gows.sdp.client.ui.fragments.OnboardingHowItWorksFragment
import com.gows.sdp.client.ui.fragments.OnboardingWelcomeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentList = listOf(
            OnboardingWelcomeFragment(),
            OnboardingHowItWorksFragment(),
            OnboardingEnjoyFragment()
        )

        val adapter = OnboardingViewPagerAdapter(this, fragmentList)
        binding.viewPager.adapter = adapter
    }
}

class OnboardingViewPagerAdapter(activity: AppCompatActivity, private val fragmentList: List<Fragment>) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = fragmentList.size
    override fun createFragment(position: Int): Fragment = fragmentList[position]
}