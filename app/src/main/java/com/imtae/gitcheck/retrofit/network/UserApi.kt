package com.imtae.gitcheck.retrofit.network

import com.imtae.gitcheck.retrofit.domain.User
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface UserApi {
    @GET("user")
    fun getUserInfo( @Header("Authorization") token: String ): Observable<User>

    @GET("user")
    fun getUserInfoTest( @Header("Authorization") token: String ): Call<User>
}