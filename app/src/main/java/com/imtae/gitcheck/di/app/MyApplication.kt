package com.imtae.gitcheck.di.app

import android.app.Application
import com.imtae.gitcheck.di.modules.activityModule
import com.imtae.gitcheck.di.modules.appModule
import com.imtae.gitcheck.di.modules.fragmentModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(
                appModule,
                activityModule,
                fragmentModule
            )
        }
    }
}