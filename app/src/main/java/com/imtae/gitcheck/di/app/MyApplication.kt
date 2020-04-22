package com.imtae.gitcheck.di.app

import android.app.Application
import com.imtae.gitcheck.di.modules.activityModule
import com.imtae.gitcheck.di.modules.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    companion object {
        val API_URL = "https://api.github.com/"
        val BASE_URL = "https://github.com/"
        val CLIENT_ID = "b497a2006a7b5989d601"
        val CLIENT_SECRET = "5d3bd68276e41fde9b374ab2b86964fd7949271d"
        val redirect_uri = "gitcheck://callback"
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(
                appModule,
                activityModule
            )
        }
    }
}