package com.imtae.gitcheck.retrofit

import com.imtae.gitcheck.di.app.MyApplication
import com.imtae.gitcheck.retrofit.network.ContributionApi
import com.imtae.gitcheck.retrofit.network.TokenApi
import com.imtae.gitcheck.retrofit.network.UserApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {

    companion object {

        private val logInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        private val client = OkHttpClient.Builder()
            .addInterceptor(logInterceptor)
            .build()

        fun getToken(): TokenApi {

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
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .baseUrl(MyApplication.API_URL)
                .build()

            return retrofit.create(UserApi::class.java)
        }

        fun getContribution(): ContributionApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .baseUrl(MyApplication.CONTIRBUTION_URL)
                .build()

            return retrofit.create(ContributionApi::class.java)
        }
    }
}