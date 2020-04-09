package com.imtae.gitcheck.di.modules

import com.imtae.gitcheck.ui.contract.MainContract
import com.imtae.gitcheck.ui.presenter.MainPresenter
import org.koin.dsl.module

val activityModule = module {

    factory<MainContract.Presenter> {
        (view : MainContract.View) -> MainPresenter(view)
    }
}