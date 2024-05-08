package com.example.vkinternshiptask.di.component

import com.example.vkinternshiptask.MainActivity
import dagger.Subcomponent
import javax.inject.Scope

@Subcomponent
@ScreenScope
interface ProductComponent {


    @Subcomponent.Factory
    interface Factory {
        fun create(): ProductComponent
    }

    fun inject(activity: MainActivity)
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ScreenScope