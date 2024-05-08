package com.example.vkinternshiptask.di.factory

import com.example.vkinternshiptask.buisness.productCard.presentation.ProductCardViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
interface ProductViewModelFactory {

    fun create(@Assisted id: String): ProductCardViewModel.ProductViewModelFactory
}