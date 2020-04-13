package com.imtae.gitcheck.ui

import android.os.Bundle
import android.widget.Toast
import com.imtae.gitcheck.R
import com.imtae.gitcheck.base.BaseActivity
import com.imtae.gitcheck.ui.contract.MainContract
import com.imtae.gitcheck.utils.ProgressUtil
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : BaseActivity(), MainContract.View {

    override val presenter: MainContract.Presenter by inject { parametersOf(this) }
    override val progress : ProgressUtil by inject { parametersOf(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }

    override fun showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun showProgress() = progress.show()

    override fun hideProgress() = progress.hide()
}
