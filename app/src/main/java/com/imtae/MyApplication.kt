package com.imtae

import android.app.Application
import com.imtae.gitcheck.di.modules.activityModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(
                activityModule
            )
        }
    }
}