package com.imtae.gitcheck.ui.presenter

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.imtae.gitcheck.retrofit.data.Key
import com.imtae.gitcheck.retrofit.domain.Contribution
import com.imtae.gitcheck.retrofit.domain.ContributionDTO
import com.imtae.gitcheck.retrofit.domain.Contributions
import com.imtae.gitcheck.retrofit.domain.User
import com.imtae.gitcheck.retrofit.repository.ContributionRepository
import com.imtae.gitcheck.retrofit.repository.UserRepository
import com.imtae.gitcheck.utils.RxBus
import com.imtae.gitcheck.ui.contract.ProfileContract
import com.imtae.gitcheck.utils.PreferenceManager
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import kotlin.collections.ArrayList

class ProfilePresenter(
    override val view: ProfileContract.View,
    private val contribution: ContributionRepository,
    private val user: UserRepository
) : ProfileContract.Presenter, KoinComponent {

    private val rxBus : RxBus by inject()

    private val pref : PreferenceManager by inject { parametersOf(this) }

    private val currentDate : String by inject(named("getCurrentDate"))

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override val contributionList = MutableLiveData<ArrayList<ContributionDTO>>()

    override val todayCommit = MutableLiveData<Int>()

    override val userInfo = MutableLiveData<User>()

    override fun getUserProfile() {

        view.showProgress()

        addDisposable(
            Single.zip(
                getContributions(pref.getUserInfo(Key.User_Info.toString()).login),
                getUserInfo(),
                { contribution: Contribution, user: User -> Pair(contribution, user) }
            )
                .doOnSuccess { view.hideProgress() }
                .subscribe{ it ->
                    setContributions(it.first)

                    pref.setUserInfo(Key.User_Info.toString(), it.second).apply {
                        rxBus.publish(it.second)
                        userInfo.postValue(it.second)
                    }
                }
        )
    }

    override fun getSearchProfile(userName: String) {

        view.showProgress()

        addDisposable(
            Single.zip(
                getContributions(userName),
                getTodayContribution(userName),
                { contribution: Contribution, todayContribution: Contributions -> Pair(contribution, todayContribution) }
            )
                .doOnSuccess { view.hideProgress() }
                .subscribe{ it ->
                    setContributions(it.first)
                    todayCommit.postValue(it.second.count)
                }
        )

    }

    private fun getContributions(userName: String): Single<Contribution> = contribution.getContribution(userName)

    private fun setContributions(contribution: Contribution) {

        val _contributionList = ArrayList<ContributionDTO>()

        for (year in contribution.years!!) {
            val contributionDTO = ContributionDTO()

            contributionDTO.year = year.year
            contributionDTO.total = year.total

            val contributionInfoList = ArrayList<ContributionDTO.ContributionInfo>()

            addDisposable(
                Observable.range(0, contribution.contributions!!.size - 1)
                    .map { contribution.contributions[it] }
                    .filter { it.date!!.contains(year.year!!) }
                    .subscribe {
                        val contributionInfo = ContributionDTO.ContributionInfo(it.date!!, it.count, it.color!!)
                        contributionInfoList.add(contributionInfo)
                    }
            )

            contributionDTO.contributionInfoList = contributionInfoList

            _contributionList.add(contributionDTO)
        }

        contributionList.postValue(_contributionList)
    }

    private fun getUserInfo(): Single<User> = pref.getData(Key.Access_Token.toString())!!.let { user.getUserInfo(it) }

    private fun getTodayContribution(userName: String) = contribution.getDesiredContribution(userName, currentDate)

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun clearDisposable() = compositeDisposable.clear()
}