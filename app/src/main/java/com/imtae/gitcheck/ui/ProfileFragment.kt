package com.imtae.gitcheck.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.drawerlayout.widget.DrawerLayout

import com.imtae.gitcheck.R
import com.imtae.gitcheck.base.BaseFragment
import com.imtae.gitcheck.retrofit.domain.User
import com.imtae.gitcheck.ui.contract.ProfileContract
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class ProfileFragment : BaseFragment(), ProfileContract.View {

    override val presenter: ProfileContract.Presenter by inject { parametersOf(this) }
    private val user : User by inject(named("getUserInfo"))
    override lateinit var binding: ViewDataBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.drawer_layout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.drawer_layout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        presenter.clearDisposable()
    }

    override fun init() {}

    override fun hideKeyboard() {}

    override fun hideProgress() {}

    override fun showKeyboard() {}

    override fun showProgress() {}

    override fun showToast(message: String) {}

    override fun startActivity(activityName: Class<*>) {}

}
