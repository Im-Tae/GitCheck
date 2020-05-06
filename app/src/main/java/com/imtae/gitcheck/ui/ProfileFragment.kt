package com.imtae.gitcheck.ui

import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.core.view.setMargins
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout

import com.imtae.gitcheck.R
import com.imtae.gitcheck.base.BaseFragment
import com.imtae.gitcheck.databinding.FragmentProfileBinding
import com.imtae.gitcheck.retrofit.domain.Contribution
import com.imtae.gitcheck.retrofit.domain.User
import com.imtae.gitcheck.ui.contract.ProfileContract
import com.imtae.gitcheck.utils.ProgressUtil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import java.time.LocalDate

class ProfileFragment : BaseFragment(), ProfileContract.View {

    override val presenter: ProfileContract.Presenter by inject { parametersOf(this) }
    private val progress : ProgressUtil by inject { parametersOf(this.context) }
    val user : User by inject(named("getUserInfo"))

    override lateinit var binding: FragmentProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.drawer_layout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.profile = this

        init()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.drawer_layout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        presenter.clearDisposable()
    }

    override fun init() {
        Picasso.get().load(user.avatar_url).into(binding.image)

        presenter.getContribution(user.login)
    }

    override fun hideKeyboard() {}

    override fun hideProgress() = progress.hide()

    override fun showKeyboard() {}

    override fun showProgress() = progress.show()

    override fun showToast(message: String) {}

    override fun startActivity(activityName: Class<*>) {}

}
