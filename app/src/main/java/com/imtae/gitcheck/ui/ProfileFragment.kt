package com.imtae.gitcheck.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

import com.imtae.gitcheck.R
import com.imtae.gitcheck.adapter.ContributionAdapter
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

class ProfileFragment : BaseFragment(), ProfileContract.View {

    override val presenter: ProfileContract.Presenter by inject { parametersOf(this) }
    private val progress : ProgressUtil by inject { parametersOf(this.context) }

    override lateinit var binding: FragmentProfileBinding

    var user = MutableLiveData<User>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.drawer_layout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.lifecycleOwner = this
        binding.profile = this

        init()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.drawer_layout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        presenter.clearDisposable()
    }

    override fun init() {

        if (arguments?.getString("name") != null)
            presenter.getContribution(arguments?.getString("name")!!)
        else
            presenter.getUserInfo()

        presenter.userInfo.observe(viewLifecycleOwner, Observer {
            user.postValue(it)
        })
    }

    override fun setUI(contributionList: ArrayList<ContributionDTO>) {
        recyclerView.adapter?.notifyDataSetChanged()
        recyclerView.adapter = ContributionAdapter(contributionList)
    }

    override fun setUserProfile(userInfo: User) {
        Picasso.get().load(userInfo.avatar_url).into(binding.image)
        presenter.getContribution(userInfo.login)
    }

    override fun hideKeyboard() {}

    override fun hideProgress() = progress.hide()

    override fun showKeyboard() {}

    override fun showProgress() = progress.show()

    override fun showToast(message: String) {}

    override fun startActivity(activityName: Class<*>) {}

}
