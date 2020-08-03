package com.imtae.gitcheck.ui.presenter

import android.util.Log
import com.imtae.gitcheck.BuildConfig
import com.imtae.gitcheck.retrofit.data.Key
import com.imtae.gitcheck.retrofit.domain.AccessToken
import com.imtae.gitcheck.retrofit.domain.User
import com.imtae.gitcheck.retrofit.network.TokenApi
import com.imtae.gitcheck.retrofit.network.UserApi
import com.imtae.gitcheck.retrofit.repository.UserRepository
import com.imtae.gitcheck.ui.MainActivity
import com.imtae.gitcheck.ui.contract.LoginContract
import com.imtae.gitcheck.utils.PreferenceManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.HttpUrl
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class LoginPresenter(override val view: LoginContract.View, private val user: UserRepository) : LoginContract.Presenter, KoinComponent {

    private val pref : PreferenceManager by inject { parametersOf(this) }

    private val getToken : TokenApi by inject(named("TokenApi"))
    private val getUserInfo : UserApi by inject(named("UserApi"))

    override fun loginGithub() {
        val httpUrl = HttpUrl.Builder()
            .scheme("http")
            .host("github.com")
            .addPathSegment("login")
            .addPathSegment("oauth")
            .addPathSegment("authorize")
            .addQueryParameter("client_id", BuildConfig.CLIENT_ID)
            .addQueryParameter("scope", "user:email")
            .addQueryParameter("redirect_uri", BuildConfig.REDIRECT_URI)
            .build()

        view.showGithubWebView(httpUrl.toString())
    }

    override fun getGithubToken(code: String, state: String) {

        addDisposable(
            getToken.getAccessToken(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET, code, BuildConfig.REDIRECT_URI, state)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith (object : DisposableObserver<AccessToken>() {
                    override fun onNext(accessToken: AccessToken) {
                        pref.setData(Key.Access_Token.toString(), " token ${accessToken.accessToken}")
                        getUserInfo()
                    }

                    override fun onComplete() {}

                    override fun onError(e: Throwable) { Log.d("error", e.message.toString()) }
                } )
        )
    }

    private fun getUserInfo() {
        addDisposable(
            user.getUserInfo()
                .subscribe(
                    { pref.setUserInfo(Key.User_Info.toString(), it) },
                    { Log.d("error", it.message.toString()) },
                    { view.startActivity(MainActivity::class.java) }
                )
        )
    }

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun clearDisposable() = compositeDisposable.clear()
}