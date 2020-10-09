package com.imtae.gitcheck.di

import com.imtae.gitcheck.view.ui.login.LoginContract
import com.imtae.gitcheck.view.ui.main.MainContract
import com.imtae.gitcheck.view.ui.splash.SplashContract
import com.imtae.gitcheck.view.ui.login.LoginPresenter
import com.imtae.gitcheck.view.ui.main.MainPresenter
import com.imtae.gitcheck.view.ui.splash.SplashPresenter
import org.koin.dsl.module

val activityModule = module {

    factory<SplashContract.Presenter> {
        (view: SplashContract.View) -> SplashPresenter(view)
    }

    factory<LoginContract.Presenter> {
        (view : LoginContract.View) -> LoginPresenter(view, get())
    }

    factory<MainContract.Presenter> {
        (view: MainContract.View) -> MainPresenter(view, get())
    }
}