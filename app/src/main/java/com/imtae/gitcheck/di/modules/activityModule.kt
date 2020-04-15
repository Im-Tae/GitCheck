package com.imtae.gitcheck.di.modules

import com.imtae.gitcheck.ui.contract.MainContract
import com.imtae.gitcheck.ui.contract.SplashContract
import com.imtae.gitcheck.ui.presenter.MainPresenter
import com.imtae.gitcheck.ui.presenter.SplashPresenter
import org.koin.dsl.module

val activityModule = module {

    factory<SplashContract.Presenter> {
            (view: SplashContract.View) -> SplashPresenter(view)
    }

    factory<MainContract.Presenter> {
            (view: MainContract.View) -> MainPresenter(view)
    }
}