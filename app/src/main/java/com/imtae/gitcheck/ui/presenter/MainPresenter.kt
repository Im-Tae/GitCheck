package com.imtae.gitcheck.ui.presenter

import com.imtae.gitcheck.retrofit.data.Key
import com.imtae.gitcheck.retrofit.domain.User
import com.imtae.gitcheck.ui.LoginActivity
import com.imtae.gitcheck.ui.SplashActivity
import com.imtae.gitcheck.ui.contract.MainContract
import com.imtae.gitcheck.utils.PreferenceManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class MainPresenter(override val view: MainContract.View) : MainContract.Presenter, KoinComponent {

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val pref : PreferenceManager by inject { parametersOf(this) }

    override fun searchUser() {

    }

    override fun logout() {
        pref.setData(Key.Access_Token.toString(), Key.NULL.toString())
        view.startActivity(LoginActivity::class.java)
    }

    override fun getUserData() : User {
        view.showProgress()

        addDisposable(
            Observable.just(view.init())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { view.hideProgress() }
        )

        return pref.getUserInfo(Key.User_Info.toString())
    }

    override fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun clearDisposable() = compositeDisposable.clear()
}