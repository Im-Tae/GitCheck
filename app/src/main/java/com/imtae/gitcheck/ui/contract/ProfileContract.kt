package com.imtae.gitcheck.ui.contract

import com.imtae.gitcheck.base.BasePresenter
import com.imtae.gitcheck.base.BaseView

class ProfileContract {
    interface View : BaseView<Presenter>

    interface Presenter : BasePresenter<View> {
        fun getContribution(id: String)
    }
}