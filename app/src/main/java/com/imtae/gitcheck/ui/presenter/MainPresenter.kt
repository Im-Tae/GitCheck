package com.imtae.gitcheck.ui.presenter

import com.imtae.gitcheck.retrofit.data.Key
import com.imtae.gitcheck.retrofit.domain.User
import com.imtae.gitcheck.ui.LoginActivity
import com.imtae.gitcheck.ui.contract.MainContract
import com.imtae.gitcheck.utils.PreferenceManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class MainPresenter(override val view: MainContract.View) : MainContract.Presenter, KoinComponent {

    private val pref : PreferenceManager by inject { parametersOf(this) }

    val user : User by inject(named("getUserInfo"))

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun searchUser() {

    }

    override fun getUserInfo(): User = user

    override fun logout() {
        pref.setData(Key.Access_Token.toString(), Key.NULL.toString())
        view.startActivity(LoginActivity::class.java)
    }

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun clearDisposable() = compositeDisposable.clear()
}