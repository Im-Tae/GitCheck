package com.imtae.gitcheck.retrofit.network

import com.imtae.gitcheck.retrofit.domain.Contribution
import com.imtae.gitcheck.retrofit.domain.Contributions
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ContributionApi {
    @GET("/user/{user}")
    fun getContributions(@Path("user") user: String): Single<Contribution>

    @GET("/user/{user}/today")
    fun getTodayContribution(@Path("user") user: String): Single<Contributions>
}