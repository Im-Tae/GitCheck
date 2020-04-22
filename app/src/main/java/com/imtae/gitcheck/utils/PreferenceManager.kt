package com.imtae.gitcheck.utils

import android.content.Context
import android.preference.PreferenceManager

@Suppress("DEPRECATION")
class PreferenceManager(context : Context) {

    private val pref = PreferenceManager.getDefaultSharedPreferences(context)

    fun getData(key: String): String? = pref.getString(key, null)

    fun setData(key : String, value : String) = pref.edit().putString(key, value).commit()
}