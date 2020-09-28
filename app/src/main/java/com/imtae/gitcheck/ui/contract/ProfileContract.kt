package com.imtae.gitcheck.ui.contract

import androidx.lifecycle.MutableLiveData
import com.imtae.gitcheck.base.BasePresenter
import com.imtae.gitcheck.base.BaseView
import com.imtae.gitcheck.retrofit.domain.ContributionDTO
import com.imtae.gitcheck.retrofit.domain.User

class ProfileContract {
    interface View : BaseView<Presenter> {

        fun setUserProfile(userInfo: User)
    }

    interface Presenter : BasePresenter<View> {

        val userInfo: MutableLiveData<User>

        val contributionList : MutableLiveData<ArrayList<ContributionDTO>>

        val todayCommit : MutableLiveData<Int>

        fun getContributions(userName: String)

        fun getTodayContribution(userName: String)

        fun getUserInfo()
    }
}