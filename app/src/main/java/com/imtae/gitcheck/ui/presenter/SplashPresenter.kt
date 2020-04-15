package com.imtae.gitcheck.ui.presenter

import com.imtae.gitcheck.ui.contract.SplashContract

class SplashPresenter(override val view: SplashContract.View) : SplashContract.Presenter {

    override fun checkUserInfo() {
        // sharedPreference 통해서 login 정보 구현, 가져오기 구현
        // 정보 있을 시에 MainActivity 로, 없을 시에 LoginActivity 로
    }
}