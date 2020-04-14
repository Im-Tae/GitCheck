package com.imtae.gitcheck.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.WindowManager
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity

abstract class BaseActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}