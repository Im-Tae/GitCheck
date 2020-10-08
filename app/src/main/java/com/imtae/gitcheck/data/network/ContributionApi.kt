package com.imtae.gitcheck.data.network

import com.imtae.gitcheck.data.domain.Contribution
import com.imtae.gitcheck.data.domain.Contributions
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ContributionApi {
    @GET("/user/{user}")
    fun getContributions(@Path("user") user: String): Single<Contribution>

    @GET("/user/{user}/today")
    fun getTodayContribution(@Path("user") user: String): Single<Contributions>

    @GET("/user/{user}/{date}")
    fun getDesiredContribution(@Path("user") user: String, @Path("date") date: String): Single<Contributions>
}