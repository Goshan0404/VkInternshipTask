package com.example.vkinternshiptask.di.component

import com.example.vkinternshiptask.di.module.RetrofitModule
import com.example.vkinternshiptask.di.module.ProductListModule
import com.example.vkinternshiptask.di.module.ProductModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class, ProductModule::class, ProductListModule::class])
interface AppComponent {

    fun productComponent(): ProductComponent.Factory
}