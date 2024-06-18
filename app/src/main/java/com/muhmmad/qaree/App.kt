package com.muhmmad.qaree

import android.app.Application
import android.content.res.Configuration
import com.google.firebase.FirebaseApp
import com.muhmmad.qaree.ui.activity.base.BaseActivity
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val local = Locale.ENGLISH
        BaseActivity.currentLocale = local
        Locale.setDefault(local)
        Configuration().setLocale(local)
        FirebaseApp.initializeApp(this)
    }
}