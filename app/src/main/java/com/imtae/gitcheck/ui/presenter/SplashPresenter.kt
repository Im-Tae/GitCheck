package com.imtae.gitcheck.ui.presenter

import com.imtae.gitcheck.ui.contract.SplashContract
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class SplashPresenter(override val view: SplashContract.View) : SplashContract.Presenter {

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun checkUserInfo() {
        // sharedPreference 통해서 login 정보 구현, 가져오기 구현
        // 정보 있을 시에 MainActivity 로, 없을 시에 LoginActivity 로

        compositeDisposable.add(
            Observable.interval(1000 * 3, TimeUnit.MILLISECONDS)
                .subscribe {  }
        )
    }

    override fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun clearDisposable() = compositeDisposable.clear()
}