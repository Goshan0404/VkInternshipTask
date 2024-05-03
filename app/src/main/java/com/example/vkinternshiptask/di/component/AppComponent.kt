package com.example.vkinternshiptask.di.component

import com.example.vkinternshiptask.di.module.AppModule
import com.example.vkinternshiptask.di.module.ProductModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ProductModule::class])
interface AppComponent {

    fun productComponent(): ProductComponent.Factory
}