package com.imtae.gitcheck.ui

import android.content.Intent
import android.os.Bundle
import com.imtae.gitcheck.R
import com.imtae.gitcheck.base.BaseActivity
import com.imtae.gitcheck.ui.contract.SplashContract
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SplashActivity : BaseActivity(), SplashContract.View {

    override val presenter: SplashContract.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        presenter.checkUserInfo()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.clearDisposable()
    }

    override fun init() {}

    override fun startActivity(activityName : Class<*>) {
        startActivity(Intent(this, activityName))
        finish()
    }

    override fun hideKeyboard() {}

    override fun hideProgress() {}

    override fun showKeyboard() {}

    override fun showProgress() {}

    override fun showToast(message: String) {}
}
