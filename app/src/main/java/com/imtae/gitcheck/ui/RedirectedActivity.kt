package com.imtae.gitcheck.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.imtae.gitcheck.di.app.MyApplication

class RedirectedActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uri: Uri? = intent.data
        if (uri != null && uri.toString().startsWith(MyApplication.redirect_uri)) {
            val intent: Intent = Intent(this, MainActivity::class.java)
            intent.putExtra("code", uri.getQueryParameter("code"))
            intent.putExtra("state", uri.getQueryParameter("state"))
            startActivity(intent)
            finish()
        }
    }
}