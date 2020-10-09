package com.imtae.gitcheck.di

import com.imtae.gitcheck.view.ui.profile.ProfileContract
import com.imtae.gitcheck.view.ui.profile.ProfilePresenter
import org.koin.dsl.module

val fragmentModule = module {
    factory<ProfileContract.Presenter> {
        (view: ProfileContract.View) -> ProfilePresenter(view, get(), get())
    }
}