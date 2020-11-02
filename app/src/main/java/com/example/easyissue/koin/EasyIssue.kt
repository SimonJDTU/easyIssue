package com.example.easyissue.koin

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class EasyIssue: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@EasyIssue)
            modules(listOf(appModule))
        }
    }
}