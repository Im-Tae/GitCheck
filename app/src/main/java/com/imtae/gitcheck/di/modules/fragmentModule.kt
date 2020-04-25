package com.imtae.gitcheck.di.modules

import com.imtae.gitcheck.ui.contract.ProfileContract
import com.imtae.gitcheck.ui.presenter.ProfilePresenter
import org.koin.dsl.module

val fragmentModule = module {
    factory<ProfileContract.Presenter> {
        (view: ProfileContract.View) -> ProfilePresenter(view)
    }
}