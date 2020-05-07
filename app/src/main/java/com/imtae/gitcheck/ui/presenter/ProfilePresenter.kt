package com.imtae.gitcheck.ui.presenter

import android.util.Log
import com.imtae.gitcheck.retrofit.RetrofitHelper
import com.imtae.gitcheck.retrofit.domain.Contribution
import com.imtae.gitcheck.retrofit.domain.ContributionDTO
import com.imtae.gitcheck.ui.contract.ProfileContract
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlin.collections.ArrayList

class ProfilePresenter(override val view: ProfileContract.View) : ProfileContract.Presenter {

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val getContribution = RetrofitHelper.getContribution()
    private val contributionList = ArrayList<ContributionDTO>()

    override fun getContribution(id: String) {

        view.showProgress()

        addDisposable(
            getContribution.getContributions(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith (object : DisposableObserver<Contribution>() {

                    override fun onNext(contribution: Contribution) {
                        Log.d("contribution", contribution.toString())

                        getContributions(contribution)

                        view.setUI(contributionList)
                    }

                    override fun onComplete() = view.hideProgress()

                    override fun onError(e: Throwable) { Log.d("error", e.message.toString()) }
                } )
        )
    }

    private fun getContributions(contribution: Contribution) {

        for (year in contribution.years!!) {
            val contributionDTO = ContributionDTO()

            contributionDTO.year = year.year
            contributionDTO.total = year.total

            val contributionInfoList = ArrayList<ContributionDTO.ContributionInfo>()

            addDisposable(
                Observable.range(0, contribution.contributions!!.size -1)
                    .map { contribution.contributions[contribution.contributions.size - it - 1] }
                    .filter { it.date!!.contains(year.year!!) }
                    .subscribe {
                        val contributionInfo = ContributionDTO.ContributionInfo(it.date!!, it.count, it.color!!, it.intensity)
                        contributionInfoList.add(contributionInfo)
                    }
            )

            contributionDTO.contributionInfoList = contributionInfoList
            this.contributionList.add(contributionDTO)
        }
    }

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun clearDisposable() = compositeDisposable.clear()
}