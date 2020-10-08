package com.imtae.gitcheck.di.modules

import android.annotation.SuppressLint
import android.content.Context
import com.imtae.gitcheck.data.Key.Key
import com.imtae.gitcheck.utils.RxBus
import com.imtae.gitcheck.utils.NetworkUtil
import com.imtae.gitcheck.utils.PreferenceManager
import com.imtae.gitcheck.utils.ProgressUtil
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
val appModule = module {
    factory { (context: Context) -> ProgressUtil(context) }

    single { PreferenceManager(androidContext()) }

    single(named("getUserInfo")) { PreferenceManager(androidContext()).getUserInfo(Key.User_Info.toString()) }

    single { NetworkUtil(androidContext()) }

    single { RxBus }

    single(named("getCurrentDate")) { SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time) }
}