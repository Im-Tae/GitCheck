package com.imtae.gitcheck.retrofit.repository
import com.imtae.gitcheck.retrofit.data.Key
import com.imtae.gitcheck.retrofit.domain.User
import com.imtae.gitcheck.retrofit.network.UserApi
import com.imtae.gitcheck.utils.PreferenceManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class UserRepository : KoinComponent {

    private val pref : PreferenceManager by inject { parametersOf(this) }
    private val getUserInfo : UserApi by inject(named("UserApi"))

    fun getUserInfo(): Observable<User> {
        return getUserInfo.getUserInfo(pref.getData(Key.Access_Token.toString())!!)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}