package com.imtae.gitcheck.ui.contract

import com.imtae.gitcheck.base.BasePresenter
import com.imtae.gitcheck.base.BaseView
import com.imtae.gitcheck.retrofit.domain.ContributionDTO

class ProfileContract {
    interface View : BaseView<Presenter> {
        fun setUI(contributionList: ArrayList<ContributionDTO>)
    }

    interface Presenter : BasePresenter<View> {
        fun getContribution(id: String)
    }
}