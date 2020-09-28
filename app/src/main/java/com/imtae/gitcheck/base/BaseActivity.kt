package com.imtae.gitcheck.base

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity

abstract class BaseActivity<B: ViewDataBinding>(
    @LayoutRes private val layoutResId: Int,
    private val BR: Int
) : RxAppCompatActivity() {

    lateinit var binding: B

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        //resources.configuration.locales.get(0).language
        //Resources.getSystem().configuration.locales.get(0)


        binding = DataBindingUtil.setContentView(this, layoutResId)
        binding.setVariable(BR, this)
        binding.lifecycleOwner = this
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}