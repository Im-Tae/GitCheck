package com.imtae.gitcheck.retrofit

import com.google.gson.GsonBuilder
import com.imtae.gitcheck.di.app.MyApplication
import com.imtae.gitcheck.retrofit.network.TokenApi
import com.imtae.gitcheck.retrofit.network.UserApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {

    companion object {

        fun getToken(): TokenApi {
            val logInterceptor = HttpLoggingInterceptor()
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(logInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .baseUrl(MyApplication.BASE_URL)
                .build()

            return retrofit.create(TokenApi::class.java)
        }

        fun getUserInfo(): UserApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(MyApplication.API_URL)
                .build()

            return retrofit.create(UserApi::class.java)
        }
    }
}