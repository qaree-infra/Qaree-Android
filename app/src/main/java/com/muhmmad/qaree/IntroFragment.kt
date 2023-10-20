package com.muhmmad.qaree

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.viewpager2.widget.ViewPager2
import com.muhmmad.qaree.databinding.FragmentIntroBinding
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle

class IntroFragment : Fragment() {
    private lateinit var binding: FragmentIntroBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIntroBinding.inflate(layoutInflater)
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

            }
            btnStart.setOnClickListener {

            }
        }
    }

    private fun initViewPager() {
        binding.viewPager.adapter = IntroAdapter()
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
        binding.indicator.setupWithViewPager(binding.viewPager)
        binding.indicator.setSliderColor(R.color.gray_indicator, R.color.blue)
        binding.indicator.setSliderHeight(20F)
        binding.indicator.setSliderWidth(20F)
        binding.indicator.setSlideMode(IndicatorSlideMode.SMOOTH)
        binding.indicator.setIndicatorStyle(IndicatorStyle.DASH)
        binding.indicator.setPageSize(3)
        binding.indicator.notifyDataChanged()
    }
}