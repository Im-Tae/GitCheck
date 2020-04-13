package com.imtae.gitcheck.ui.presenter

import com.imtae.gitcheck.ui.contract.MainContract

class MainPresenter(override val view: MainContract.View) : MainContract.Presenter {

    override fun setToastMessage() {
        view.showToast("sdsd")
    }

    override fun progress() {
        view.showProgress()
    }
}