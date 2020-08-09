package com.imtae.gitcheck.ui.presenter

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.imtae.gitcheck.retrofit.data.Key
import com.imtae.gitcheck.retrofit.domain.Contribution
import com.imtae.gitcheck.retrofit.domain.ContributionDTO
import com.imtae.gitcheck.retrofit.domain.User
import com.imtae.gitcheck.retrofit.network.ContributionApi
import com.imtae.gitcheck.retrofit.repository.UserRepository
import com.imtae.gitcheck.utils.RxBus
import com.imtae.gitcheck.ui.contract.ProfileContract
import com.imtae.gitcheck.utils.PreferenceManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import kotlin.collections.ArrayList

class ProfilePresenter(override val view: ProfileContract.View, private val user: UserRepository) : ProfileContract.Presenter, KoinComponent {

    private val getContribution : ContributionApi by inject(named("ContributionApi"))

    private val rxBus : RxBus by inject()

    private val pref : PreferenceManager by inject { parametersOf(this) }

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val contributionList = ArrayList<ContributionDTO>()

    override var userInfo = MutableLiveData<User>()

    override fun getContribution(id: String) {

        view.showProgress()

        addDisposable(
            getContribution.getContributions(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        Log.d("contribution", it.toString())

                        getContributions(it)

                        view.apply {
                            setUI(contributionList)
                            hideProgress()
                        }
                    },
                    { Log.d("error", it.message.toString()) }
                )
        )
    }

    private fun getContributions(contribution: Contribution) {

        for (year in contribution.years!!) {
            val contributionDTO = ContributionDTO()

            contributionDTO.year = year.year
            contributionDTO.total = year.total

            val contributionInfoList = ArrayList<ContributionDTO.ContributionInfo>()

            addDisposable(
                Observable.range(0, contribution.contributions!!.size - 1)
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

    override fun getUserInfo() {

        val token = pref.getData(Key.Access_Token.toString())!!

        addDisposable(
            user.getUserInfo(token)
                .subscribe(
                    {
                        pref.setUserInfo(Key.User_Info.toString(), it).apply {
                            rxBus.publish(it)
                            userInfo.value = it
                            view.setUserProfile(it)
                        }
                    },
                    { Log.d("error", it.message.toString()) }
                )
        )
    }

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun clearDisposable() = compositeDisposable.clear()
}