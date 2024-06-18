package com.muhmmad.qaree.ui.activity.base

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.muhmmad.qaree.databinding.ErrorLayoutBinding
import com.muhmmad.qaree.databinding.LoadingLayoutBinding
import com.muhmmad.qaree.databinding.ToastLayoutBinding
import com.muhmmad.qaree.ui.activity.auth.AuthActivity
import com.muhmmad.qaree.ui.activity.home.HomeActivity
import java.util.Locale

open class BaseActivity : AppCompatActivity() {
    private val loading: Dialog by lazy {
        getLoading(this)
    }

    init {
        updateConfig(this)
    }

    private fun updateConfig(wrapper: ContextThemeWrapper) {
        currentLocale?.let { Locale.setDefault(it) }
        val configuration = Configuration()
        configuration.setLocale(currentLocale)
        wrapper.applyOverrideConfiguration(configuration)
    }

    private fun getLoading(context: Context): Dialog {
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

    fun showLoading(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) addBlurEffect(view)
        loading.show()
    }

    fun dismissLoading(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) removeBlurEffect(view)
        loading.dismiss()
    }

    fun showError(view: View, error: String) {
        dismissLoading(view)
        val errorBinding = ErrorLayoutBinding.inflate(LayoutInflater.from(this))
        errorBinding.message.text = error
        val toast = Toast(this)
        toast.duration = Toast.LENGTH_SHORT

        toast.view = errorBinding.root
        toast.show()
    }

    fun showMessage(message: String) {
        val toastBinding = ToastLayoutBinding.inflate(LayoutInflater.from(this))
        toastBinding.message.text = message
        val toast = Toast(this)
        toast.duration = Toast.LENGTH_LONG
        toast.view = toastBinding.root
        toast.show()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun addBlurEffect(view: View) {
        view.setRenderEffect(
            RenderEffect.createBlurEffect(
                10F,
                10F,
                Shader.TileMode.MIRROR
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun removeBlurEffect(view: View) {
        view.setRenderEffect(null)
    }

    fun goToHome(context: Context) {
        startActivity(Intent(context, HomeActivity::class.java))
        finish()
    }

    fun goToAuth(context: Context) {
        startActivity(Intent(context, AuthActivity::class.java))
        finish()
    }

    companion object {
        var currentLocale: Locale? = null
    }
}