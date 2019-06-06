package com.mycv.android

import android.app.Application
import com.mycv.android.core.DependencyLocator

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        DependencyLocator.initInstance(applicationContext)
    }
}