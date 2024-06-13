package com.muhmmad.qaree.ui.activity.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
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
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            showMessage(getString(R.string.the_app_can_not_show_notifications))
        }
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

            checkNotificationPermission()
        }
    }

    override fun onNewIntent(newIntent: Intent?) {
        super.onNewIntent(newIntent)
        intent = newIntent
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

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.

                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}