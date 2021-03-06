package com.imtae.gitcheck.view.ui.profile

import androidx.lifecycle.MutableLiveData
import com.imtae.gitcheck.base.BasePresenter
import com.imtae.gitcheck.base.BaseView
import com.imtae.gitcheck.data.domain.ContributionDTO
import com.imtae.gitcheck.data.domain.User

class ProfileContract {
    interface View : BaseView<Presenter> {

        fun showUserNotFoundUI()
    }

    interface Presenter : BasePresenter<View> {

        val userInfo: MutableLiveData<User>

        val contributionList : MutableLiveData<ArrayList<ContributionDTO>>

        val todayCommit : MutableLiveData<Int>

        fun getUserProfile()

        fun getSearchProfile(userName: String)
    }
}