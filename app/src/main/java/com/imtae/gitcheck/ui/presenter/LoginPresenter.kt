package com.imtae.gitcheck.ui.presenter

import android.util.Log
import com.imtae.gitcheck.di.app.MyApplication
import com.imtae.gitcheck.data.Key
import com.imtae.gitcheck.retrofit.RetrofitHelper
import com.imtae.gitcheck.retrofit.domain.AccessToken
import com.imtae.gitcheck.retrofit.domain.User
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

class LoginPresenter(override val view: LoginContract.View) : LoginContract.Presenter, KoinComponent {

    private val pref : PreferenceManager by inject { parametersOf(this) }
    private val getToken = RetrofitHelper.getToken()
    private val getUserInfo = RetrofitHelper.getUserInfo()

    override fun loginGithub() {
        val httpUrl = HttpUrl.Builder()
            .scheme("http")
            .host("github.com")
            .addPathSegment("login")
            .addPathSegment("oauth")
            .addPathSegment("authorize")
            .addQueryParameter("client_id", MyApplication.CLIENT_ID)
            .addQueryParameter("scope", "user:email")
            .addQueryParameter("redirect_uri", MyApplication.redirect_uri)
            .build()

        view.showGithubWebView(httpUrl.toString())
    }

    override fun getGithubToken(code: String, state: String) {

        addDisposable(
            getToken.getAccessToken(MyApplication.CLIENT_ID, MyApplication.CLIENT_SECRET, code, MyApplication.redirect_uri, state)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith (object : DisposableObserver<AccessToken>() {
                    override fun onNext(accessToken: AccessToken) {
                        pref.setData(Key.Access_Token.toString(), accessToken.accessToken)
                        getUserName("token ${accessToken.accessToken}")
                    }

                    override fun onComplete() {}

                    override fun onError(e: Throwable) { Log.d("error", e.message.toString()) }
                } )
        )
    }

    private fun getUserName(token: String) {
        addDisposable(
            getUserInfo.getUserInfo(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : DisposableObserver<User>() {

                    override fun onNext(user: User) {
                        Log.d("userInfo", user.toString())
                        pref.setUserInfo(Key.User_Info.toString(), user)
                    }

                    override fun onComplete() = view.startActivity(MainActivity::class.java)

                    override fun onError(e: Throwable) { Log.d("error", e.message.toString()) }
                })
        )
    }

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun clearDisposable() = compositeDisposable.clear()
}