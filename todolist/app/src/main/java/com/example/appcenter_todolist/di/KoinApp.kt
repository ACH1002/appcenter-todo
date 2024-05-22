package com.example.appcenter_todolist.di

import android.app.Application
import com.example.appcenter_todolist.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KoinApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KoinApp)
            modules(viewModelModule)
        }
    }

}