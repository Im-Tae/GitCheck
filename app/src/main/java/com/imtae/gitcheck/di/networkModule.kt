package com.imtae.gitcheck.di

import com.imtae.gitcheck.BuildConfig
import com.imtae.gitcheck.data.network.ContributionApi
import com.imtae.gitcheck.data.network.TokenApi
import com.imtae.gitcheck.data.network.UserApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single { GsonConverterFactory.create() as Converter.Factory }

    single { RxJava2CallAdapterFactory.create() as CallAdapter.Factory }

    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    single {
        Retrofit.Builder()
            .addConverterFactory(get())
            .addCallAdapterFactory(get())
            .client(get())
    }

    single(named("TokenApi")) {

        Retrofit.Builder()
            .addConverterFactory(get())
            .addCallAdapterFactory(get())
            .client(get())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
            .create(TokenApi::class.java)
    }

    single(named("UserApi")) {

        Retrofit.Builder()
            .addConverterFactory(get())
            .addCallAdapterFactory(get())
            .client(get())
            .baseUrl(BuildConfig.API_URL)
            .build()
            .create(UserApi::class.java)
    }

    single(named("ContributionApi")) {

        Retrofit.Builder()
            .addConverterFactory(get())
            .addCallAdapterFactory(get())
            .client(get())
            .baseUrl(BuildConfig.CONTRIBUTION_URL)
            .build()
            .create(ContributionApi::class.java)
    }
}