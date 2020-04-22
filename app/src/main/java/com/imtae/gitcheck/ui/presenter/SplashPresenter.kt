package com.imtae.gitcheck.ui.presenter

import com.imtae.gitcheck.data.Key
import com.imtae.gitcheck.ui.LoginActivity
import com.imtae.gitcheck.ui.MainActivity
import com.imtae.gitcheck.ui.contract.SplashContract
import com.imtae.gitcheck.utils.NetworkUtil
import com.imtae.gitcheck.utils.PreferenceManager
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import java.util.concurrent.TimeUnit

class SplashPresenter(override val view: SplashContract.View) : SplashContract.Presenter, KoinComponent {

    override val compositeDisposable : CompositeDisposable = CompositeDisposable()

    private val pref : PreferenceManager by inject { parametersOf(this) }
    private val networkUtil : NetworkUtil by inject { parametersOf(this) }

    override fun checkUserInfo() {

        when {
            pref.getData(Key.Access_Token.toString()) != null && networkUtil.networkInfo() -> view.startActivity(MainActivity::class.java)
            !networkUtil.networkInfo() -> {}
            else -> {
                addDisposable(
                    Observable.interval(1000 * 3, TimeUnit.MILLISECONDS)
                        .subscribe { view.startActivity(LoginActivity::class.java) }
                )
            }
        }
    }

    override fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun clearDisposable() = compositeDisposable.clear()
}