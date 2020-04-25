package com.imtae.gitcheck.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding

import com.imtae.gitcheck.R
import com.imtae.gitcheck.base.BaseFragment
import com.imtae.gitcheck.ui.contract.ProfileContract
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ProfileFragment : BaseFragment(), ProfileContract.View {

    override val presenter: ProfileContract.Presenter by inject { parametersOf(this) }
    override lateinit var binding: ViewDataBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun init() {}

    override fun hideKeyboard() {}

    override fun hideProgress() {}

    override fun showKeyboard() {}

    override fun showProgress() {}

    override fun showToast(message: String) {}

    override fun startActivity(activityName: Class<*>) {}

    override fun onDestroy() {
        super.onDestroy()
        presenter.clearDisposable()
    }

}
