package com.imtae.gitcheck.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.imtae.gitcheck.R
import com.imtae.gitcheck.base.BaseActivity
import com.imtae.gitcheck.ui.contract.LoginContract
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class LoginActivity : BaseActivity(), LoginContract.View {

    override val presenter: LoginContract.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button.setOnClickListener { presenter.loginGithub() }
    }

    override fun init() {}

    override fun hideKeyboard() {}

    override fun showKeyboard() {}

    override fun showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun hideProgress() {}

    override fun showProgress() {}

    override fun startActivity(activityName: Class<*>) = startActivity(Intent(this, activityName))

}
