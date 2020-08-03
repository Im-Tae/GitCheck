package com.imtae.gitcheck.di.modules

import com.imtae.gitcheck.BuildConfig
import com.imtae.gitcheck.retrofit.network.ContributionApi
import com.imtae.gitcheck.retrofit.network.TokenApi
import com.imtae.gitcheck.retrofit.network.UserApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single { GsonConverterFactory.create() as Converter.Factory }

    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    single {

        Retrofit.Builder()
            .addConverterFactory(get())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(get())
    }

    single(named("TokenApi")) {

        Retrofit.Builder()
            .addConverterFactory(get())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(get())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
            .create(TokenApi::class.java)
    }

    single(named("UserApi")) {

        Retrofit.Builder()
            .addConverterFactory(get())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(get())
            .baseUrl(BuildConfig.API_URL)
            .build()
            .create(UserApi::class.java)
    }

    single(named("ContributionApi")) {

        Retrofit.Builder()
            .addConverterFactory(get())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(get())
            .baseUrl(BuildConfig.CONTRIBUTION_URL)
            .build()
            .create(ContributionApi::class.java)
    }
}