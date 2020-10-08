package com.imtae.gitcheck.data.repository

import com.imtae.gitcheck.data.domain.User
import com.imtae.gitcheck.data.network.UserApi
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

class UserRepository : KoinComponent {

    private val userApi : UserApi by inject(named("UserApi"))

    private val networkStatus: NetworkUtil by inject { parametersOf(this) }

    fun getUserInfo(token: String): Single<User> =
        userApi.getUserInfo(token)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .retryWhen {
                Flowable.interval(3, TimeUnit.SECONDS)
                    .onBackpressureDrop()
                    .retryUntil {
                        if(networkStatus.networkInfo())
                            return@retryUntil true

                        false
                    }
            }
}