package com.imtae.gitcheck.retrofit.repository

import com.imtae.gitcheck.retrofit.domain.Contribution
import com.imtae.gitcheck.retrofit.domain.Contributions
import com.imtae.gitcheck.retrofit.network.ContributionApi
import com.imtae.gitcheck.utils.NetworkUtil
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import java.util.concurrent.TimeUnit

class ContributionRepository : KoinComponent {

    private val contributionApi : ContributionApi by inject(named("ContributionApi"))

    private val networkStatus: NetworkUtil by inject { parametersOf(this) }

    fun getContribution(userName: String): Single<Contribution> =
        contributionApi.getContributions(userName)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .retryWhen {
                Flowable.interval(3, TimeUnit.SECONDS)
                    .retryUntil {
                        if(networkStatus.networkInfo())
                            return@retryUntil true

                        false
                    }
            }

    fun getTodayContribution(userName: String): Single<Contributions> =
        contributionApi.getTodayContribution(userName)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
}