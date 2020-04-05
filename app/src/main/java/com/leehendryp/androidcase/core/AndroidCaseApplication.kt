package com.leehendryp.androidcase.core

import android.app.Application
import com.leehendryp.androidcase.core.di.AppComponent
import com.leehendryp.androidcase.core.di.DaggerAppComponent

class AndroidCaseApplication : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}