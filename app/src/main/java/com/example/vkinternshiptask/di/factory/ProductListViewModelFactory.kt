package com.example.vkinternshiptask.di.factory

import android.app.Application
import com.example.vkinternshiptask.buisness.productList.presentation.ProductListViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
interface ProductListViewModelFactory {

    fun create(@Assisted context: Application): ProductListViewModel.ProductsListSavedStateViewModelFactory
}