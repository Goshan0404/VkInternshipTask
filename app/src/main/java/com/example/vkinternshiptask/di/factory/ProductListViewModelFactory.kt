package com.example.vkinternshiptask.di.factory

import android.app.Application
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import com.example.vkinternshiptask.productList.view.ProductListViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
interface ProductListViewModelFactory {

    fun create(@Assisted context: Application): ProductListViewModel.ProductsListSavedStateViewModelFactory
}