package com.imtae.gitcheck.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.imtae.gitcheck.BR
import com.imtae.gitcheck.R
import com.imtae.gitcheck.base.BaseActivity
import com.imtae.gitcheck.databinding.ActivityMainBinding
import com.imtae.gitcheck.databinding.NavigationHeaderBinding
import com.imtae.gitcheck.data.domain.User
import com.imtae.gitcheck.ui.contract.MainContract
import com.imtae.gitcheck.utils.KeyboardUtil
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tool_bar.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : BaseActivity<ActivityMainBinding>(
    R.layout.activity_main,
    BR.main
), MainContract.View {

    override val presenter: MainContract.Presenter by inject { parametersOf(this) }

    var userInfo : User = presenter.getUserInfo()

    private lateinit var bindingNavigationHeader : NavigationHeaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingNavigationHeader = NavigationHeaderBinding.bind(navigation_view.getHeaderView(0))
        bindingNavigationHeader.headerNavigation = this

        init()
    }

    override fun onResume() {
        super.onResume()
        presenter.updateUserInfo()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.clearDisposable()
    }

    @SuppressLint("SetTextI18n")
    override fun init() {

        presenter.getTodayContribution()

        navigation_view.setNavigationItemSelectedListener(this)
        show_navigation_bar_button.setOnClickListener(this)
        search_button.setOnClickListener(this)
        back_button.setOnClickListener(this)

        bindingNavigationHeader.headerLayout.setOnClickListener(this)

        search_bar.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    presenter.addDisposable(
                        Observable.just(hideKeyboard())
                            .subscribe {
                                presenter.searchUser(search_bar.text.toString())
                            }
                    )
                    return true
                }
                return false
            }
        })

        presenter.todayCommit.observe(this, { commit_textView.text = "${commit_textView.text} $it" })

    }

    override fun showFragment(fragment: Fragment) {

        setToolbarMain()

        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_up,0,0, R.anim.slide_down)
            .add(R.id.drawer_layout, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.show_navigation_bar_button ->
                presenter.addDisposable(
                    Observable.just(drawer_layout.openDrawer(GravityCompat.START))
                        .subscribe()
                )

            R.id.search_button ->
                presenter.addDisposable(
                    Observable.just(setToolbarSearch(), search_bar.requestFocus(), showKeyboard())
                        .subscribe()
                )

            R.id.back_button ->
                presenter.addDisposable(
                    Observable.just(hideKeyboard())
                        .subscribe { setToolbarMain() }
                )

            R.id.header_layout ->
                presenter.addDisposable(
                    Observable.just(hideNavigationDrawer())
                        .subscribe {
                            showFragment(ProfileFragment())
                        }
                )
        }
    }

    override fun onNavigationItemSelected(item : MenuItem): Boolean {

        when(item.itemId) {
            R.id.nav_settings -> {}
            R.id.nav_logout -> {
                presenter.logout()
                finish()
            }
        }

        return false
    }

    private fun setToolbarMain() {
        search_bar.visibility = View.INVISIBLE
        back_button.visibility = View.INVISIBLE
        search_button.visibility = View.VISIBLE
        show_navigation_bar_button.visibility = View.VISIBLE

        search_bar.setText("")
    }

    private fun setToolbarSearch() {
        search_bar.visibility = View.VISIBLE
        back_button.visibility = View.VISIBLE
        show_navigation_bar_button.visibility = View.INVISIBLE
        search_button.visibility = View.GONE
    }

    override fun setUser(user: User) { userInfo = user }

    override fun startActivity(activityName: Class<*>) = startActivity(Intent(this, activityName))

    override fun hideNavigationDrawer() = drawer_layout.closeDrawers()

    override fun hideKeyboard() = KeyboardUtil.hideKeyboard(this.currentFocus, this)

    override fun showKeyboard() = KeyboardUtil.showKeyboard(this.currentFocus, this)

    override fun showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun showProgress() {
        commit_textView.visibility = View.INVISIBLE
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        commit_textView.visibility = View.VISIBLE
        progress_bar.visibility = View.INVISIBLE
    }

    override fun onBackPressed() {

        when {
            drawer_layout.isDrawerOpen(GravityCompat.START) -> hideNavigationDrawer()
            search_bar.visibility == View.VISIBLE -> setToolbarMain()
            else -> super.onBackPressed()

        }
    }
}
