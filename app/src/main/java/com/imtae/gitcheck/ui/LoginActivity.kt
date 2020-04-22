package com.imtae.gitcheck.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.imtae.gitcheck.MyApplication
import com.imtae.gitcheck.R
import com.imtae.gitcheck.base.BaseActivity
import com.imtae.gitcheck.databinding.ActivityLoginBinding
import com.imtae.gitcheck.ui.contract.LoginContract
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.util.concurrent.TimeUnit

class LoginActivity : BaseActivity(), LoginContract.View {

    override val presenter: LoginContract.Presenter by inject { parametersOf(this) }
    override lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.login = this
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

    override fun init() = presenter.loginGithub()

    override fun showGithubWebView(url : String) = startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))

    override fun hideKeyboard() {}

    override fun showKeyboard() {}

    override fun showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun hideProgress() {}

    override fun showProgress() {}

    override fun startActivity(activityName: Class<*>) {
        startActivity(Intent(this, activityName))
        finish()
    }

}
