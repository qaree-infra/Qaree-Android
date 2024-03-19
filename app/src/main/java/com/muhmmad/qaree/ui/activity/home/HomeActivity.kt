package com.muhmmad.qaree.ui.activity.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.ActivityHomeBinding
import com.muhmmad.qaree.ui.activity.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }
    private val context: Context by lazy {
        binding.root.context
    }
    private val nav: NavController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.apply {
            handleClicks()

            val navHostFragment =
                supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment

            navHostFragment.navController.addOnDestinationChangedListener { controller, destination, arguments ->
                handleBottomNavigation(destination.id)
                run {
                    binding.appBar.root.isVisible =
                        arguments?.getBoolean("ShowAppBar", true) == true
                    binding.bottomNavigation.isVisible =
                        arguments?.getBoolean("showBottomNavigation", true) == true
                }
            }
        }
    }

    private fun handleClicks() {
        binding.apply {
            llHome.setOnClickListener {
                nav.navigate(R.id.homeFragment)
            }
            llChat.setOnClickListener {
                nav.navigate(R.id.inboxFragment)
            }
            llLibrary.setOnClickListener {
                nav.navigate(R.id.libraryFragment)
            }
            appBar.ivSearch.setOnClickListener {
                nav.navigate(R.id.searchFragment)
            }
            appBar.ivNotification.setOnClickListener {
                nav.navigate(R.id.notificationFragment)
            }
            appBar.ivProfile.setOnClickListener {
                nav.navigate(R.id.profileFragment)
            }
        }
    }

    private fun handleBottomNavigation(id: Int) {
        clearBottomNavigation()
        when (id) {
            R.id.homeFragment -> {
                binding.llHome.background =
                    AppCompatResources.getDrawable(context, R.drawable.nav_item_bg)
                binding.ivHome.visibility = View.VISIBLE
                binding.tvHome.visibility = View.VISIBLE
                binding.ivDisabledHome.visibility = View.GONE
            }

            R.id.libraryFragment, R.id.shelfFragment -> {
                binding.llLibrary.background =
                    AppCompatResources.getDrawable(context, R.drawable.nav_item_bg)
                binding.ivLibrary.visibility = View.VISIBLE
                binding.tvLibrary.visibility = View.VISIBLE
                binding.ivDisabledLibrary.visibility = View.GONE
            }

            R.id.inboxFragment -> {
                binding.llChat.background =
                    AppCompatResources.getDrawable(context, R.drawable.nav_item_bg)
                binding.ivChat.visibility = View.VISIBLE
                binding.tvChat.visibility = View.VISIBLE
                binding.ivDisabledChat.visibility = View.GONE
            }
        }
    }

    private fun clearBottomNavigation() {
        binding.apply {
            llHome.setBackgroundColor(resources.getColor(R.color.white, theme))
            llLibrary.setBackgroundColor(resources.getColor(R.color.white, theme))
            llChat.setBackgroundColor(resources.getColor(R.color.white, theme))
            ivHome.visibility = View.GONE
            tvHome.visibility = View.GONE
            ivLibrary.visibility = View.GONE
            tvLibrary.visibility = View.GONE
            ivChat.visibility = View.GONE
            tvChat.visibility = View.GONE
            ivDisabledHome.visibility = View.VISIBLE
            ivDisabledLibrary.visibility = View.VISIBLE
            ivDisabledChat.visibility = View.VISIBLE
        }
    }
}

private const val TAG = "HomeActivity"