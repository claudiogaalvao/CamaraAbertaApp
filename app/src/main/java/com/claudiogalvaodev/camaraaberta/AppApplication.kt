package com.claudiogalvaodev.camaraaberta

import android.app.Application
import com.claudiogalvaodev.camaraaberta.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppApplication)
            modules(appModules)
        }
    }

}