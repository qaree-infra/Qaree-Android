package com.muhmmad.qaree.ui.activity.main

import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
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
import java.util.Locale

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

    init {
        updateConfig(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.apply {
            //Handle action bar
            handleActionBar()

            loading = getLoading()
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
        setSupportActionBar(binding.toolBar)
        //Show navigation button
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        // Set up the AppBarConfiguration with your navigation graph
        val appBarConfiguration = AppBarConfiguration(nav.graph, null)
        setupActionBarWithNavController(nav, appBarConfiguration)
        binding.toolBar.setNavigationOnClickListener {
            nav.navigateUp()
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment

        navHostFragment.navController.addOnDestinationChangedListener { controller, destination, arguments ->
            run {
                binding.toolBar.isVisible = arguments?.getBoolean("ShowAppBar", true) == true
            }
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

    }

    fun goToHome() {

    }

    companion object {
        val currentLocale: Locale = Locale("en")
    }
}