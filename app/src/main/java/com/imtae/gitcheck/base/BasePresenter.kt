package com.imtae.gitcheck.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

interface BasePresenter<T> {

    val view: T
    val compositeDisposable : CompositeDisposable

    fun addDisposable(disposable: Disposable)

    fun clearDisposable()
}