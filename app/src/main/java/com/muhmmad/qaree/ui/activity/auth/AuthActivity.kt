package com.muhmmad.qaree.ui.activity.auth

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.ActivityAuthBinding
import com.muhmmad.qaree.ui.activity.base.BaseActivity
import com.muhmmad.qaree.ui.activity.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthActivity : BaseActivity() {
    private val binding: ActivityAuthBinding by lazy {
        ActivityAuthBinding.inflate(layoutInflater)
    }
    private val nav: NavController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.apply {
            //Handle action bar
            handleActionBar()

            val navHostFragment =
                supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment

            navHostFragment.navController.addOnDestinationChangedListener { controller, destination, arguments ->
                run {
                    binding.appBar.root.isVisible =
                        arguments?.getBoolean("ShowAppBar", true) == true
                }
            }
        }
    }

    private fun handleActionBar() {
        setSupportActionBar(binding.appBar.toolBar)
        //Show navigation button
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        // Set up the AppBarConfiguration with your navigation graph
        try {
            val appBarConfiguration = AppBarConfiguration(nav.graph, null)
            setupActionBarWithNavController(nav, appBarConfiguration)
            binding.appBar.toolBar.setNavigationOnClickListener {
                nav.navigateUp()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}