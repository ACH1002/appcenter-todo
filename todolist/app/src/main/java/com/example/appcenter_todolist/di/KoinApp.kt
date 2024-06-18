package com.example.appcenter_todolist.di

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.appcenter_todolist.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class KoinApp : Application() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@KoinApp)
            modules(viewModelModule)
        }
    }

}