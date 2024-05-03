package com.example.vkinternshiptask.di

import android.app.Application
import com.example.vkinternshiptask.di.component.AppComponent
import com.example.vkinternshiptask.di.component.DaggerAppComponent

class App: Application() {
    lateinit var applicationComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerAppComponent.create()
    }

}