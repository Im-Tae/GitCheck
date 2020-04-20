package com.imtae.gitcheck.base

import com.imtae.gitcheck.utils.ProgressUtil

interface BaseView<T> {

    val presenter : T

    fun showProgress()

    fun hideProgress()

    fun hideKeyboard()

    fun showKeyboard()

    fun showToast(message: String)

    fun init()

    fun startActivity(activityName : Class<*>)
}