package com.imtae.gitcheck.di.modules

import android.content.Context
import com.imtae.gitcheck.utils.PreferenceManager
import com.imtae.gitcheck.utils.ProgressUtil
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    factory { (context: Context) -> ProgressUtil(context) }

    single { PreferenceManager(androidContext()) }
}