package com.imtae.gitcheck.base

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.WindowManager
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity

abstract class BaseActivity : RxAppCompatActivity() {

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        //resources.configuration.locales.get(0).language
        //Resources.getSystem().configuration.locales.get(0)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}