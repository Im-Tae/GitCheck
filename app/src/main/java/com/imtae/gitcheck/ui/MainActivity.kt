package com.imtae.gitcheck.ui

import android.os.Bundle
import com.imtae.gitcheck.R
import com.imtae.gitcheck.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
