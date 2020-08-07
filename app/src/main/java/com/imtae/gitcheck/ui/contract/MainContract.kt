package com.imtae.gitcheck.ui.contract

import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.imtae.gitcheck.base.BasePresenter
import com.imtae.gitcheck.base.BaseView
import com.imtae.gitcheck.retrofit.domain.User

class MainContract {

    interface View : BaseView<Presenter>, NavigationView.OnNavigationItemSelectedListener, android.view.View.OnClickListener {

        fun hideNavigationDrawer()

        fun setUser(user: User)

        fun showFragment(fragment: Fragment)
    }

    interface Presenter : BasePresenter<View> {

        fun searchUser(name: String)

        fun logout()

        fun getUserInfo(): User

        fun updateUserInfo()
    }
}