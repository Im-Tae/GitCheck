package com.imtae.gitcheck.ui.contract

import com.imtae.gitcheck.base.BasePresenter
import com.imtae.gitcheck.base.BaseView

class MainContract {

    interface View : BaseView<Presenter> {

    }

    interface Presenter : BasePresenter<View>
}