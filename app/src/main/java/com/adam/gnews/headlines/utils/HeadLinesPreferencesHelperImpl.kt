package com.adam.gnews.headlines.utils

import android.content.SharedPreferences
import androidx.core.content.edit

class HeadLinesPreferencesHelperImpl(
    private val sharedPreferences: SharedPreferences
) : HeadLinesPreferencesHelper {

    override fun getInt(key: String, defaultValue: Int): Int =
        sharedPreferences.getInt(key, defaultValue)

    override fun putInt(key: String, value: Int) =
        sharedPreferences.edit { putInt(key, value) }

    override fun remove(key: String) {
        sharedPreferences.edit { remove(key) }
    }
}