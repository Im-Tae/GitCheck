@file:Suppress("DEPRECATION")

package com.imtae.gitcheck.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.imtae.gitcheck.data.Key.Key
import com.imtae.gitcheck.data.domain.User


class PreferenceManager(context : Context) {

    private val pref: SharedPreferences by lazy {
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

        EncryptedSharedPreferences.create(
            "GitCheck",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun getData(key: String): String? = pref.getString(key, Key.NULL.toString())

    fun setData(key : String, value : String) = pref.edit().putString(key, value).commit()

    fun getUserInfo(key: String) : User = GsonBuilder().create().fromJson(pref.getString(key, Gson().toJson(User())), User::class.java)

    fun setUserInfo(key : String, user : User) = pref.edit().putString(key, GsonBuilder().create().toJson(user, User::class.java)).commit()

}