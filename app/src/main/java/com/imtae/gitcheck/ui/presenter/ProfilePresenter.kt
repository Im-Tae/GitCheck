package com.imtae.gitcheck.ui.presenter

import com.imtae.gitcheck.ui.contract.ProfileContract
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class ProfilePresenter(override val view: ProfileContract.View) : ProfileContract.Presenter {

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun clearDisposable() = compositeDisposable.clear()
}