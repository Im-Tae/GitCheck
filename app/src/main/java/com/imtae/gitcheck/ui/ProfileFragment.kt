package com.imtae.gitcheck.ui

import android.os.Bundle
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.imtae.gitcheck.BR

import com.imtae.gitcheck.R
import com.imtae.gitcheck.ui.adapter.ContributionAdapter
import com.imtae.gitcheck.base.BaseFragment
import com.imtae.gitcheck.databinding.FragmentProfileBinding
import com.imtae.gitcheck.retrofit.domain.ContributionDTO
import com.imtae.gitcheck.retrofit.domain.User
import com.imtae.gitcheck.ui.contract.ProfileContract
import com.imtae.gitcheck.utils.ProgressUtil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    R.layout.fragment_profile,
    BR.profile
), ProfileContract.View {

    override val presenter: ProfileContract.Presenter by inject { parametersOf(this) }

    private val progress : ProgressUtil by inject { parametersOf(this.context) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.drawer_layout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        presenter.clearDisposable()
    }

    override fun init() {

        if (arguments?.getString("name") != null) {
            userInfoLayout.visibility = View.GONE
            presenter.getContributions(arguments?.getString("name")!!)
        }
        else
            presenter.getUserInfo()
    }

    override fun setUI(contributionList: ArrayList<ContributionDTO>) {
        recyclerView.adapter?.notifyDataSetChanged()
        recyclerView.adapter = ContributionAdapter(contributionList)
    }

    override fun setUserProfile(userInfo: User) {
        Picasso.get().load(userInfo.avatar_url).into(binding.image)
        presenter.getContributions(userInfo.login)
    }

    override fun hideKeyboard() {}

    override fun hideProgress() = progress.hide()

    override fun showKeyboard() {}

    override fun showProgress() = progress.show()

    override fun showToast(message: String) {}

    override fun startActivity(activityName: Class<*>) {}
}
