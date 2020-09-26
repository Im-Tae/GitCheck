package com.imtae.gitcheck.ui.presenter

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.imtae.gitcheck.retrofit.data.Key
import com.imtae.gitcheck.retrofit.domain.User
import com.imtae.gitcheck.retrofit.repository.ContributionRepository
import com.imtae.gitcheck.utils.RxBus
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

class MainPresenter(override val view: MainContract.View, private val contribution: ContributionRepository) : MainContract.Presenter, KoinComponent {

    private val pref : PreferenceManager by inject { parametersOf(this) }

    val user : User by inject(named("getUserInfo"))

    private val rxBus : RxBus by inject()

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
            contribution.getTodayContribution(user.name!!)
                .subscribe({
                    todayCommit.value = it.count
                    view.hideProgress()
                },{})
        )
    }

    override fun getUserInfo(): User = user

    override fun updateUserInfo() {
        addDisposable(
            rxBus.listen(User::class.java).subscribe {
                view.setUser(it)
            }
        )
    }

    override fun logout() {
        pref.setData(Key.Access_Token.toString(), Key.NULL.toString())
        view.startActivity(LoginActivity::class.java)
    }

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun clearDisposable() = compositeDisposable.clear()
}