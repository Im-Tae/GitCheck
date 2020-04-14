package com.imtae.gitcheck.base

import io.reactivex.disposables.CompositeDisposable

interface BasePresenter<T> {

    val view: T
}