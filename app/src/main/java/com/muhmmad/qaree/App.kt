package com.muhmmad.qaree

import android.app.Application
import android.content.res.Configuration
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val local = Locale("en")
        Locale.setDefault(local)
        Configuration().setLocale(local)
    }
}