package com.imtae.gitcheck.base

import android.app.Activity
import com.imtae.gitcheck.utils.ProgressUtil
import io.reactivex.disposables.CompositeDisposable

interface BaseView<T> {

    val presenter : T
    val progress : ProgressUtil
    val compositeDisposable : CompositeDisposable

    fun showProgress()

    fun hideProgress()

    fun hideKeyboard()

    fun showKeyboard()

    fun showToast(message: String)

    fun init()

    fun startActivity(activityName : Activity)
}