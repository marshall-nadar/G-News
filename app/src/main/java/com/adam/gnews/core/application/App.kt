package com.adam.gnews.core.application

import android.app.Application
import com.adam.gnews.BuildConfig
import com.adam.gnews.core.application.di.CoreComponent
import com.adam.gnews.core.application.di.CoreModule
import com.adam.gnews.core.application.di.DaggerCoreComponent
import com.facebook.stetho.Stetho
import com.squareup.picasso.Picasso
import javax.inject.Inject

class App : Application() {

    companion object {
        lateinit var coreComponent: CoreComponent
    }

    @Inject
    lateinit var picasso: Picasso

    override fun onCreate() {
        super.onCreate()
        coreComponent = DaggerCoreComponent.builder()
            .coreModule(CoreModule(context = this))
            .build()
        coreComponent.inject(this)

        initialisePicasso()
        initialiseStetho()
    }

    private fun initialiseStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(applicationContext)
        }
    }

    private fun initialisePicasso() {
        // To make sure Picasso (with Okhttp3Downloader) is initialized only once
        Picasso.setSingletonInstance(picasso)
    }
}