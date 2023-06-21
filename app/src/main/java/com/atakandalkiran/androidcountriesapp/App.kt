package com.atakandalkiran.androidcountriesapp

import android.app.Application
import android.content.Context
import com.atakandalkiran.androidcountriesapp.util.AppThemeHelper
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        application = this
    }

    private fun setTheme() {
        val mode = AppThemeHelper.getSelectedThemeMode(this)
        AppThemeHelper.setTheme(mode)
    }

    companion object {
        private lateinit var application: Application

        fun getAppContext(): Context {
            return application.applicationContext
        }
    }
}
