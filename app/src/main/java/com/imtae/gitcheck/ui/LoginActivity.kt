package com.imtae.gitcheck.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.imtae.gitcheck.MyApplication
import com.imtae.gitcheck.R
import com.imtae.gitcheck.base.BaseActivity
import com.imtae.gitcheck.ui.contract.LoginContract
import com.imtae.gitcheck.utils.PreferenceManager
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.net.HttpURLConnection

class LoginActivity : BaseActivity(), LoginContract.View {

    override val presenter: LoginContract.Presenter by inject { parametersOf(this) }
    private val pref : PreferenceManager by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
    }

    override fun onResume() {
        super.onResume()

        val uri = intent.data

        if (uri != null && uri.toString().startsWith(MyApplication.redirect_uri)) {
            val code = uri.getQueryParameter("code")
            val state = uri.getQueryParameter("state")

            presenter.getGithubToken(code.toString(), state.toString())
        }
    }

    override fun init() {
        login_button.setOnClickListener { presenter.loginGithub() }
    }

    override fun showGithubWebView(url : String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun hideKeyboard() {}

    override fun showKeyboard() {}

    override fun showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun hideProgress() {}

    override fun showProgress() {}

    override fun startActivity(activityName: Class<*>) = startActivity(Intent(this, activityName))

}
