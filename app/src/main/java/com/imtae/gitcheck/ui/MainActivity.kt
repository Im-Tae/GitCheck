package com.imtae.gitcheck.ui

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

    override fun init() {
        navigation_view.setNavigationItemSelectedListener(this)
        show_navigation_bar_button.setOnClickListener(this)
        search_button.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.show_navigation_bar_button -> {
                Observable.just(drawer_layout.openDrawer(GravityCompat.START))
                    .subscribe { hideKeyboard() }
            }

            R.id.search_button -> {
                search_bar.visibility = View.VISIBLE
                presenter.searchUser()
            }
        }
    }

    override fun onNavigationItemSelected(item : MenuItem): Boolean {

        when(item.itemId) {
            R.id.nav_settings -> {}
            R.id.login_layout -> {}
        }

        return false
    }

    override fun hideNavigationDrawer() = drawer_layout.closeDrawers()

    override fun hideKeyboard() = KeyboardUtil.hideKeyboard(this.currentFocus, this)

    override fun showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun showProgress() = progress.show()

    override fun hideProgress() = progress.hide()

    override fun onBackPressed() {

        if (drawer_layout.isDrawerOpen(GravityCompat.START))
            hideNavigationDrawer()
        else
            super.onBackPressed()
    }
}
