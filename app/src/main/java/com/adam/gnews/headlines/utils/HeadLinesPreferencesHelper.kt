package com.adam.gnews.headlines.utils

interface HeadLinesPreferencesHelper {
    fun getInt(key: String, defaultValue: Int): Int
    fun putInt(key: String, value: Int)
    fun remove(key: String)
}