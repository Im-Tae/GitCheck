package com.imtae.gitcheck.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.imtae.gitcheck.R
import com.imtae.gitcheck.base.BaseActivity
import com.imtae.gitcheck.databinding.ActivityProfileBinding
import com.imtae.gitcheck.ui.contract.ProfileContract
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ProfileActivity : BaseActivity(), ProfileContract.View {

    override val presenter: ProfileContract.Presenter by inject { parametersOf(this) }
    override lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        binding.profile = this
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.clearDisposable()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun init() {}

    override fun hideKeyboard() {}

    override fun hideProgress() {}

    override fun showKeyboard() {}

    override fun showProgress() {}

    override fun showToast(message: String) {}

    override fun startActivity(activityName: Class<*>) {}
}
