package com.imtae.gitcheck.ui.presenter

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.imtae.gitcheck.data.Key.Key
import com.imtae.gitcheck.data.domain.User
import com.imtae.gitcheck.data.repository.ContributionRepository
import com.imtae.gitcheck.ui.LoginActivity
import com.imtae.gitcheck.ui.ProfileFragment
import com.imtae.gitcheck.ui.contract.MainContract
import com.imtae.gitcheck.utils.PreferenceManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class MainPresenter(
    override val view: MainContract.View,
    private val contribution: ContributionRepository
) : MainContract.Presenter, KoinComponent {

    private val pref : PreferenceManager by inject { parametersOf(this) }

    private val currentDate : String by inject(named("getCurrentDate"))

    override val user : User by inject(named("getUserInfo"))

    override val todayCommit = MutableLiveData<Int>()

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun searchUser(name: String) {

        val bundle = Bundle()
        bundle.putString("name", name)

        val profileFragment = ProfileFragment()
        profileFragment.arguments = bundle

        view.showFragment(profileFragment)
    }

    override fun getTodayContribution() {

        view.showProgress()

        addDisposable(
            contribution.getDesiredContribution(user.login, currentDate)
                .subscribe({
                    todayCommit.postValue(it.count)
                    compositeDisposable.clear()
                    view.hideProgress()
                },{ })
        )
    }

    override fun logout() {
        pref.setData(Key.Access_Token.toString(), Key.NULL.toString())
        view.startActivity(LoginActivity::class.java)
    }

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun clearDisposable() = compositeDisposable.clear()
}