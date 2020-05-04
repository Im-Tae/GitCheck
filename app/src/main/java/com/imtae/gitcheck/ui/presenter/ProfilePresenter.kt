package com.imtae.gitcheck.ui.presenter

import android.util.Log
import com.imtae.gitcheck.retrofit.RetrofitHelper
import com.imtae.gitcheck.retrofit.domain.Contribution
import com.imtae.gitcheck.ui.contract.ProfileContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class ProfilePresenter(override val view: ProfileContract.View) : ProfileContract.Presenter {

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val getContribution = RetrofitHelper.getContribution()

    override fun getContribution(id: String) {

        view.showProgress()

        addDisposable(
            getContribution.getContributions(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith (object : DisposableObserver<Contribution>() {
                    override fun onNext(contribution: Contribution) {
                        Log.d("contribution", contribution.toString())
                    }

                    override fun onComplete() = view.hideProgress()

                    override fun onError(e: Throwable) { Log.d("error", e.message.toString()) }
                } )
        )
    }

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun clearDisposable() = compositeDisposable.clear()
}