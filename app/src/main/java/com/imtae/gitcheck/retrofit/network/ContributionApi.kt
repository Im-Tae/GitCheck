package com.imtae.gitcheck.retrofit.network

import com.imtae.gitcheck.retrofit.domain.Contribution
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ContributionApi {
    @GET("{userId}")
    fun getContributions(@Path("userId") userId: String): Observable<Contribution>
}