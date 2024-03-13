package com.muhmmad.qaree.ui.activity.on_boarding

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.ActivityOnBoardingBinding
import com.muhmmad.qaree.ui.activity.auth.AuthActivity
import com.muhmmad.qaree.ui.activity.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnBoardingActivity : BaseActivity() {
    private val binding: ActivityOnBoardingBinding by lazy {
        ActivityOnBoardingBinding.inflate(layoutInflater)
    }
    private val context: Context by lazy {
        binding.root.context
    }
    private val viewModel: OnBoardingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        initSplashScreen()
        super.onCreate(savedInstanceState)
        viewModel.isFirstTime()
        viewModel.isLogged()
//        setContentView(binding.root)
        binding.apply {
            checkStatus()
            initViewPager()
            initIndicator()
            btnNext.setOnClickListener {
                viewPager.currentItem += 1
            }
            tvSkip.setOnClickListener {
                startActivity(Intent(context, AuthActivity::class.java))
                finish()
            }
            btnStart.setOnClickListener {
                startActivity(Intent(context, AuthActivity::class.java))
                finish()
            }
        }
    }

    private fun checkStatus() {
        lifecycleScope.launch {
            viewModel.state.collect {
                if (it.isLogged != null) {
                    setTheme(R.style.Theme_Qaree)
                    if (it.isLogged) goToHome(context)
                    else {
                        if (it.isFirstTime == true) {
                            viewModel.setFirstTime()
                            super.setContentView(binding.root)
                        } else goToAuth(context)
                    }
                }
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
        TabLayoutMediator(binding.tabIndicator, binding.viewPager) { _, _ ->
        }.attach()

        for (i in 0 until binding.tabIndicator.tabCount) {
            val tab = (binding.tabIndicator.getChildAt(0) as ViewGroup).getChildAt(i)
            val p = tab.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(0, 0, 20, 0)
            tab.requestLayout()
        }
    }

    private fun initSplashScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            installSplashScreen()
        } else {
            //  setTheme(R.style.Theme_Qaree)
        }
    }
}