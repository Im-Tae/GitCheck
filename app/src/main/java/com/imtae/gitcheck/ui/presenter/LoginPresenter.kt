package com.imtae.gitcheck.ui.presenter

import com.imtae.gitcheck.ui.contract.LoginContract
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class LoginPresenter(override val view: LoginContract.View) : LoginContract.Presenter {

    override fun loginGithub() {
        view.showToast("test")
    }

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun clearDisposable() = compositeDisposable.clear()
}