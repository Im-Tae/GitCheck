package com.imtae.gitcheck.ui.presenter

import android.annotation.SuppressLint
import android.util.Log
import com.imtae.gitcheck.retrofit.RetrofitHelper
import com.imtae.gitcheck.retrofit.domain.Contribution
import com.imtae.gitcheck.ui.contract.ProfileContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

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

                    @SuppressLint("SimpleDateFormat")
                    override fun onNext(contribution: Contribution) {
                        Log.d("contribution", contribution.toString())

                        //val calendar = Calendar.getInstance().apply { time = Date() }

                        //val maxCommit = contribution.contributions?.maxBy { it.count }.toString()
                        //val nowCommit = contribution.contributions?.indexOf(contribution.contributions.find { it.date == SimpleDateFormat("yyyy-MM-dd").format(calendar.time) }).apply { calendar.add(Calendar.YEAR, -1) }
                        //val lastCommit = contribution.contributions?.indexOf(contribution.contributions.find { it.date == SimpleDateFormat("yyyy-MM-dd").format(calendar.time) })
                    }

                    override fun onComplete() = view.hideProgress()

                    override fun onError(e: Throwable) { Log.d("error", e.message.toString()) }
                } )
        )
    }

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun clearDisposable() = compositeDisposable.clear()
}