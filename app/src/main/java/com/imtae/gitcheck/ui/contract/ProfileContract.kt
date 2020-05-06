package com.imtae.gitcheck.ui.contract

import com.imtae.gitcheck.base.BasePresenter
import com.imtae.gitcheck.base.BaseView
import com.imtae.gitcheck.retrofit.domain.Contribution

class ProfileContract {
    interface View : BaseView<Presenter>

    interface Presenter : BasePresenter<View> {
        fun getContribution(id: String)
    }
}