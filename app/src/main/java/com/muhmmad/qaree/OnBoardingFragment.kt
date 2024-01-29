package com.muhmmad.qaree

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.muhmmad.qaree.databinding.FragmentOnBoardingBinding

class OnBoardingFragment : Fragment() {
    private val binding: FragmentOnBoardingBinding by lazy {
        FragmentOnBoardingBinding.inflate(layoutInflater)
    }

    private val nav: NavController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            initViewPager()
            initIndicator()
            btnNext.setOnClickListener {
                viewPager.currentItem += 1
            }
            tvSkip.setOnClickListener {
                nav.navigate(R.id.action_nav_on_boarding_to_loginFragment)
            }
            btnStart.setOnClickListener {
                nav.navigate(R.id.action_nav_on_boarding_to_loginFragment)
            }
        }
    }

    private fun initViewPager() {
        binding.viewPager.adapter = OnBoardingAdapter()
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 2) {
                    binding.tvSkip.visibility = View.GONE
                    binding.btnNext.visibility = View.GONE
                    binding.btnStart.visibility = View.VISIBLE
                } else {
                    binding.tvSkip.visibility = View.VISIBLE
                    binding.btnNext.visibility = View.VISIBLE
                    binding.btnStart.visibility = View.GONE
                }
            }
        })
    }

    private fun initIndicator() {
        TabLayoutMediator(binding.tabIndicator, binding.viewPager) { tab, position ->
        }.attach()

        for (i in 0 until binding.tabIndicator.tabCount) {
            val tab = (binding.tabIndicator.getChildAt(0) as ViewGroup).getChildAt(i)
            val p = tab.layoutParams as MarginLayoutParams
            p.setMargins(0, 0, 20, 0)
            tab.requestLayout()
        }
    }
}