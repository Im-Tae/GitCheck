package com.imtae.gitcheck.retrofit.repository
import com.imtae.gitcheck.retrofit.data.Key
import com.imtae.gitcheck.retrofit.domain.User
import com.imtae.gitcheck.retrofit.network.UserApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named

class UserRepository : KoinComponent {

    private val userApi : UserApi by inject(named("UserApi"))

    fun getUserInfo(token: String): Single<User> =
        userApi.getUserInfo(token)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
}