package com.imtae.gitcheck.base

import androidx.databinding.ViewDataBinding

interface BaseView<T> {

    val presenter : T
    val binding : ViewDataBinding

    fun showProgress()

    fun hideProgress()

    fun hideKeyboard()

    fun showKeyboard()

    fun showToast(message: String)

    fun init()

    fun startActivity(activityName : Class<*>)
}