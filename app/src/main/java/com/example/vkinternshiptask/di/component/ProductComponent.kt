package com.example.vkinternshiptask.di.component

import com.example.vkinternshiptask.MainActivity
import dagger.Subcomponent

@Subcomponent
interface ProductComponent {


    @Subcomponent.Factory
    interface Factory {
        fun create(): ProductComponent
    }

    fun inject(activity: MainActivity)
}