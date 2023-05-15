package com.rodrigoapolo.gogarage.util

import android.content.Context
import android.content.SharedPreferences

class SecurityPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("Motivation", Context.MODE_PRIVATE)

    fun storeString(key: String, value: String) {
        this.sharedPreferences.edit().putString(key, value).apply()
    }

    fun storeInt(key: String, value: Int) {
        this.sharedPreferences.edit().putInt(key, value).apply()
    }

    fun getStoredString(key: String): String {
        return this.sharedPreferences.getString(key, "") ?: ""
    }

    fun getStoredInt(key: String): Int {
        return this.sharedPreferences.getInt(key, 0)
    }

}
