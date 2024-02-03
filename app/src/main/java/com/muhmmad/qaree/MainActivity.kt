package com.muhmmad.qaree

import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.muhmmad.qaree.databinding.ActivityMainBinding
import com.muhmmad.qaree.databinding.ErrorLayoutBinding
import com.muhmmad.qaree.databinding.LoadingLayoutBinding
import com.muhmmad.qaree.databinding.ToastLayoutBinding
import com.muhmmad.qaree.ui.fragment.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
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
    private lateinit var loading: Dialog

    init {
        updateConfig(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(binding.root)
        binding.apply {
            loading = getLoading()
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

    fun showLoading() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Log.i(TAG, "DONE")
            addBlurEffect()
        }
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
        toast.duration = Toast.LENGTH_SHORT
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

    companion object {
        val currentLocale: Locale = Locale("en")
    }
}