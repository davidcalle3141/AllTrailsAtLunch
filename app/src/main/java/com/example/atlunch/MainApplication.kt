package com.example.atlunch

import android.app.Application
import com.example.atlunch.data.api.placesRetrofitModule
import com.example.atlunch.di.appModule
import com.example.atlunch.di.dataBaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(appModule, dataBaseModule, placesRetrofitModule)
        }
    }
}