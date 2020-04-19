package com.imtae.gitcheck.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.imtae.gitcheck.R
import com.imtae.gitcheck.base.BaseActivity
import com.imtae.gitcheck.ui.contract.MainContract
import com.imtae.gitcheck.utils.KeyboardUtil
import com.imtae.gitcheck.utils.ProgressUtil
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tool_bar.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : BaseActivity(), MainContract.View {

    override val presenter: MainContract.Presenter by inject { parametersOf(this) }
    override val progress : ProgressUtil by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.getData()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.clearDisposable()
    }

    override fun init() {
        navigation_view.setNavigationItemSelectedListener(this)
        show_navigation_bar_button.setOnClickListener(this)
        search_button.setOnClickListener(this)
        back_button.setOnClickListener(this)
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
        }
    }

    override fun onNavigationItemSelected(item : MenuItem): Boolean {

        when(item.itemId) {
            R.id.nav_settings -> {}
            R.id.login_layout -> {}
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

    override fun startActivity(activityName: Activity) = startActivity(Intent(this, activityName::class.java))

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
