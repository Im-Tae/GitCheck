package com.imtae.gitcheck.base

import android.app.Activity
import com.imtae.gitcheck.utils.ProgressUtil

interface BaseView<T> {

    val presenter : T
    val progress : ProgressUtil

    fun showProgress()

    fun hideProgress()

    fun hideKeyboard()

    fun showKeyboard()

    fun showToast(message: String)

    fun init()

    fun startActivity(activityName : Activity)
}