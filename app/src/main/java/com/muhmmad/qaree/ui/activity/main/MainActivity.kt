package com.muhmmad.qaree.ui.activity.main

import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.ActivityMainBinding
import com.muhmmad.qaree.databinding.ErrorLayoutBinding
import com.muhmmad.qaree.databinding.LoadingLayoutBinding
import com.muhmmad.qaree.databinding.ToastLayoutBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Locale

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val context: Context by lazy {
        binding.root.context
    }
    private val nav: NavController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }
    private lateinit var loading: Dialog
    private val viewModel: MainViewModel by viewModels()

    init {
        updateConfig(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.apply {
            viewModel.isLogged()
            //Handle action bar
            handleActionBar()
            checkStatus()
            handleClicks()

            loading = getLoading()

            val navHostFragment =
                supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment

            navHostFragment.navController.addOnDestinationChangedListener { controller, destination, arguments ->
                handleBottomNavigation(destination.id)
                handleAppBar(destination.id)
                run {
                    binding.authAppbar.root.isVisible =
                        arguments?.getBoolean("ShowAppBar", true) == true
                }
            }
        }
    }

    private fun initSplashScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            installSplashScreen()
        } else {
            setTheme(R.style.Theme_Qaree)
        }
    }

    private fun getLoading(): Dialog {
        val dialogBinding = LoadingLayoutBinding.inflate(LayoutInflater.from(context))
        val dialog = Dialog(context)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog.setCancelable(false)
        return dialog
    }

    private fun handleActionBar() {
        setSupportActionBar(binding.authAppbar.toolBar)
        //Show navigation button
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        // Set up the AppBarConfiguration with your navigation graph
        val appBarConfiguration = AppBarConfiguration(nav.graph, null)
        setupActionBarWithNavController(nav, appBarConfiguration)
        binding.authAppbar.toolBar.setNavigationOnClickListener {
            nav.navigateUp()
        }
    }

    fun showLoading() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) addBlurEffect()
        loading.show()
    }

    fun dismissLoading() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) removeBlurEffect()
        loading.dismiss()
    }

    fun showError(error: String) {
        dismissLoading()
        val errorBinding = ErrorLayoutBinding.inflate(LayoutInflater.from(context))
        errorBinding.message.text = error
        val toast = Toast(context)
        toast.duration = Toast.LENGTH_SHORT

        toast.view = errorBinding.root
        toast.show()
    }

    fun showMessage(message: String) {
        val toastBinding = ToastLayoutBinding.inflate(LayoutInflater.from(context))
        toastBinding.message.text = message
        val toast = Toast(context)
        toast.duration = Toast.LENGTH_LONG
        toast.view = toastBinding.root
        toast.show()
    }

    private fun checkStatus() {
        lifecycleScope.launch {
            viewModel.state.collect {
                if (it.isLogged != null) {
                    if (it.isLogged) goToHome() else authProcess()
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
        }
    }

    private fun handleAppBar(id: Int) {
        when (id) {
            R.id.homeFragment, R.id.libraryFragment, R.id.inboxFragment -> {
                binding.authAppbar.root.visibility = View.GONE
                binding.homeAppbar.root.visibility = View.VISIBLE
            }

            else -> {
                binding.authAppbar.root.visibility = View.VISIBLE
                binding.homeAppbar.root.visibility = View.GONE
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

            R.id.libraryFragment -> {
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

    @RequiresApi(Build.VERSION_CODES.S)
    private fun addBlurEffect() {
        binding.root.setRenderEffect(
            RenderEffect.createBlurEffect(
                10F,
                10F,
                Shader.TileMode.MIRROR
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun removeBlurEffect() {
        binding.root.setRenderEffect(null)
    }

    private fun updateConfig(wrapper: ContextThemeWrapper) {
        Locale.setDefault(currentLocale)
        val configuration = Configuration()
        configuration.setLocale(currentLocale)
        wrapper.applyOverrideConfiguration(configuration)
    }

    fun authProcess() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val authGraph = inflater.inflate(R.navigation.auth_nav)
        navHostFragment.navController.graph = authGraph

        binding.bottomNavigation.visibility = View.GONE
    }

    fun goToHome() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val homeGraph = inflater.inflate(R.navigation.home_nav)
        navHostFragment.navController.graph = homeGraph

        binding.bottomNavigation.visibility = View.VISIBLE

    }

    companion object {
        val currentLocale: Locale = Locale("en")
    }
}