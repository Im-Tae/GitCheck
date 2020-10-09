package com.imtae.gitcheck.view.ui.login

import com.imtae.gitcheck.base.BasePresenter
import com.imtae.gitcheck.base.BaseView

class LoginContract {
    interface View : BaseView<Presenter> {

        fun showGithubWebView(url : String)
    }

    interface Presenter : BasePresenter<View> {

        fun loginGithub()

        fun getGithubToken(code : String, state : String)
    }
}