package com.imtae.gitcheck.ui.presenter

import com.imtae.gitcheck.ui.contract.MainContract
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import java.util.concurrent.TimeUnit

class MainPresenter(override val view: MainContract.View) : MainContract.Presenter {

    override fun searchUser() {
        view.hideKeyboard()

    }

    override fun getData() {
        view.showProgress()
        view.init()

        // test
        Observable.interval(2, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { view.hideProgress() }
    }
}