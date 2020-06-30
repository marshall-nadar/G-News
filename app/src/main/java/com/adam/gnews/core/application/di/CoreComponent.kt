package com.adam.gnews.core.application.di

import android.content.Context
import android.content.SharedPreferences
import com.adam.gnews.core.application.App
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        CoreModule::class,
        StorageModule::class,
        NetworkModule::class
    ]
)
interface CoreComponent {

    fun context(): Context

    fun sharedPreferences(): SharedPreferences

    fun sharedPreferencesEditor(): SharedPreferences.Editor

    fun retrofit(): Retrofit

    fun gson(): Gson

    fun picasso(): Picasso

    fun inject(app: App)
}