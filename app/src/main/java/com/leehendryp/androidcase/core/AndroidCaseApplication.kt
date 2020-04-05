package com.leehendryp.androidcase.core

import android.app.Application
import com.google.android.libraries.places.api.Places
import com.leehendryp.androidcase.BuildConfig
import com.leehendryp.androidcase.core.di.AppComponent
import com.leehendryp.androidcase.core.di.DaggerAppComponent

class AndroidCaseApplication : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        Places.initialize(this, BuildConfig.GOOGLE_CLOUD_KEY)
        Places.createClient(this)
    }
}