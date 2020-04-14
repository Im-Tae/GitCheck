package com.imtae.gitcheck.ui.contract

import com.google.android.material.navigation.NavigationView
import com.imtae.gitcheck.base.BasePresenter
import com.imtae.gitcheck.base.BaseView

class MainContract {

    interface View : BaseView<Presenter>, NavigationView.OnNavigationItemSelectedListener, android.view.View.OnClickListener {
        fun hideNavigationDrawer()
    }

    interface Presenter : BasePresenter<View> {
        fun searchUser()

        fun getData()
    }
}