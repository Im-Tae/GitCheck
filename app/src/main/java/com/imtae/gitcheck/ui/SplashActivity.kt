package com.imtae.gitcheck.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.imtae.gitcheck.R
import com.imtae.gitcheck.base.BaseActivity
import com.imtae.gitcheck.ui.contract.SplashContract
import com.imtae.gitcheck.utils.ProgressUtil
import com.trello.rxlifecycle3.android.ActivityEvent
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity(), SplashContract.View {

    override val presenter: SplashContract.Presenter by inject { parametersOf(this) }
    override val progress: ProgressUtil by inject { parametersOf(this) }

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.dispose()
    }

    override fun init() {
        compositeDisposable.add(
            Observable.interval(1000 * 3, TimeUnit.MILLISECONDS)
                .subscribe { presenter.checkUserInfo() }
        )
    }

    override fun startActivity(activityName : Activity) = startActivity(Intent(this, activityName::class.java))

    override fun hideKeyboard() {}

    override fun hideProgress() {}

    override fun showKeyboard() {}

    override fun showProgress() {}

    override fun showToast(message: String) {}
}
