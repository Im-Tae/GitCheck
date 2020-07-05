@file:Suppress("DEPRECATION")

package com.imtae.gitcheck.utils

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.GsonBuilder
import com.imtae.gitcheck.retrofit.data.Key
import com.imtae.gitcheck.retrofit.domain.User


class PreferenceManager(context : Context) {

    private val pref = PreferenceManager.getDefaultSharedPreferences(context)

    fun getData(key: String): String? = pref.getString(key, Key.NULL.toString())

    fun setData(key : String, value : String) = pref.edit().putString(key, value).commit()

    fun getUserInfo(key: String) : User = GsonBuilder().create().fromJson(pref.getString(key, null), User::class.java)

    fun setUserInfo(key : String, user : User) = pref.edit().putString(key, GsonBuilder().create().toJson(user, User::class.java)).commit()

}