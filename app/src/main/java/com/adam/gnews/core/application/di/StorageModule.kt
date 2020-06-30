package com.adam.gnews.core.application.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {

    @Provides
    @Singleton
    fun providesSharedPreferences(context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    @Provides
    @Singleton
    fun providesSharedPreferencesEditor(sharedPreferences: SharedPreferences):
            SharedPreferences.Editor = sharedPreferences.edit()
}