package com.imtae.gitcheck.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import com.imtae.gitcheck.R
import com.imtae.gitcheck.base.BaseActivity
import com.imtae.gitcheck.databinding.ActivityMainBinding
import com.imtae.gitcheck.retrofit.domain.User
import com.imtae.gitcheck.ui.contract.MainContract
import com.imtae.gitcheck.utils.KeyboardUtil
import com.imtae.gitcheck.utils.ProgressUtil
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.navigation_header.view.*
import kotlinx.android.synthetic.main.navigation_header.view.header_layout
import kotlinx.android.synthetic.main.tool_bar.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : BaseActivity(), MainContract.View {

    override val presenter: MainContract.Presenter by inject { parametersOf(this) }
    private val progress : ProgressUtil by inject { parametersOf(this) }

    override lateinit var binding: ActivityMainBinding

    private val user : User = presenter.getUserData()
    private lateinit var headerView : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.main = this

        headerView = navigation_view.getHeaderView(0)

        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.clearDisposable()
    }

    override fun init() {
        navigation_view.setNavigationItemSelectedListener(this)
        show_navigation_bar_button.setOnClickListener(this)
        headerView.header_layout.setOnClickListener(this)
        search_button.setOnClickListener(this)
        back_button.setOnClickListener(this)

        setDrawerUserUI()
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.show_navigation_bar_button ->
                presenter.addDisposable(
                    Observable.just(drawer_layout.openDrawer(GravityCompat.START))
                        .subscribe()
                )

            R.id.header_layout ->
                presenter.addDisposable(
                    Observable.just(hideNavigationDrawer())
                        .subscribe {
                            supportFragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.slide_up,0,0, R.anim.slide_down)
                                .add(R.id.drawer_layout, ProfileFragment())
                                .addToBackStack(null)
                                .commit()
                        }
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

    private fun setDrawerUserUI() {
        Picasso.get().load(user.avatar_url).into(headerView.header_image)
        headerView.header_name.text = user.name
        headerView.header_nickname.text = user.login
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

    override fun startActivity(activityName: Class<*>) = startActivity(Intent(this, activityName))

    override fun hideNavigationDrawer() = drawer_layout.closeDrawers()

    override fun hideKeyboard() = KeyboardUtil.hideKeyboard(this.currentFocus, this)

    override fun showKeyboard() = KeyboardUtil.showKeyboard(this.currentFocus, this)

    override fun showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun showProgress() = progress.show()

    override fun hideProgress() = progress.hide()

    override fun onBackPressed() {

        when {
            drawer_layout.isDrawerOpen(GravityCompat.START) -> hideNavigationDrawer()
            search_bar.visibility == View.VISIBLE -> setToolbarMain()
            else -> super.onBackPressed()

        }
    }
}
