package com.example.marvelapp

import android.app.Application
import com.example.marvelapp.connection.NetworkingModules
import com.example.marvelapp.repository.RepositoryModules
import com.example.marvelapp.ui.UIModules
import com.example.marvelapp.ui.adapter.PagingModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppDelegate : Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@AppDelegate)
            modules(
                *UIModules.all,
                *RepositoryModules.all,
                *PagingModules.all,
                *NetworkingModules.all

            )
        }
    }
}