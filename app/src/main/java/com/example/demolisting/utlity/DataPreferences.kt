package com.example.demolisting.utlity

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

import java.util.*

/**
 * Created by Somenath
 */
@SuppressLint("CommitPrefEdits")
class DataPreferences(private val _context: Context) {

    private val pref: SharedPreferences
    private val editor: SharedPreferences.Editor
    private val PRIVATE_MODE = 0

    fun updateToken(VALUE: String?) {
        editor.putString(DEVICE_TOKEN, VALUE)
        editor.apply()
    }

    fun getDeviceToken(): String? {
        return pref.getString(DEVICE_TOKEN, "")
    }

    fun setLoggedIn(value: Boolean) {
        editor.putBoolean(IS_LOGGED_IN, value)
        editor.apply()
    }

    fun getLoggedInInfo(): Boolean {
        return pref.getBoolean(IS_LOGGED_IN, false)
    }

    fun setUserName(name: String) {
        editor.putString(KEY_USER_NAME, name)
        editor.apply()
    }

    fun getUserName(): String {
        return pref.getString(KEY_USER_NAME, "")!!
    }

    fun saveCommonMessage(str: String) {
        editor.putString(COMMON_MESSAGE, str)
        editor.apply()
    }

    fun getCommonMessage(): String {
        return pref.getString(COMMON_MESSAGE, "")!!
    }


    companion object {
        private const val KEY_USER_NAME = "user_name"
        private const val DEVICE_TOKEN = "device_token"
        private const val PREF_NAME = "SMARTLER"
        private const val COMMON_MESSAGE = "COMMON_MESSAGE"
        private const val IS_LOGGED_IN = "IS_LOGGED_IN"
    }

    init {
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }


}