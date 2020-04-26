package com.imtae.gitcheck.di.modules

import android.content.Context
import com.imtae.gitcheck.retrofit.data.Key
import com.imtae.gitcheck.utils.NetworkUtil
import com.imtae.gitcheck.utils.PreferenceManager
import com.imtae.gitcheck.utils.ProgressUtil
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    factory { (context: Context) -> ProgressUtil(context) }

    single { PreferenceManager(androidContext()) }

    single(named("getUserInfo")) { PreferenceManager(androidContext()).getUserInfo(Key.User_Info.toString()) }

    single { NetworkUtil(androidContext()) }
}