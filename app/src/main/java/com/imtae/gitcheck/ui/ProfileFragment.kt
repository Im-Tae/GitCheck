package com.imtae.gitcheck.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.MutableLiveData
import com.imtae.gitcheck.BR
import com.imtae.gitcheck.R
import com.imtae.gitcheck.ui.adapter.ContributionAdapter
import com.imtae.gitcheck.base.BaseFragment
import com.imtae.gitcheck.databinding.FragmentProfileBinding
import com.imtae.gitcheck.data.domain.User
import com.imtae.gitcheck.ui.contract.ProfileContract
import com.imtae.gitcheck.utils.ProgressUtil
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

    val user = MutableLiveData<User>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.drawer_layout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        presenter.clearDisposable()
    }

    @SuppressLint("SetTextI18n")
    override fun init() {

        activity?.drawer_layout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        val userName = arguments?.getString("name") ?: ""

        if (userName.isNotEmpty()) {
            userInfoLayout.visibility = View.GONE
            presenter.getSearchProfile(userName)
        }
        else presenter.getUserProfile()

        presenter.contributionList.observe(viewLifecycleOwner, {
            recyclerView.adapter?.notifyDataSetChanged()
            recyclerView.adapter = ContributionAdapter(it)
        })

        presenter.todayCommit.observe(viewLifecycleOwner, {
            fragment_commit_textView.visibility = View.VISIBLE
            fragment_commit_textView.text = "${fragment_commit_textView.text} $it"
        })

        presenter.userInfo.observe(viewLifecycleOwner, { user.postValue(it) })
    }

    override fun showUserNotFoundUI() {
        profile_view.visibility = View.GONE
        user_not_found_textView.visibility = View.VISIBLE
    }

    override fun hideKeyboard() {}

    override fun hideProgress() = progress.hide()

    override fun showKeyboard() {}

    override fun showProgress() = progress.show()

    override fun showToast(message: String) {}

    override fun startActivity(activityName: Class<*>) {}
}
