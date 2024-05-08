package com.example.vkinternshiptask.di.module

import com.example.vkinternshiptask.buisness.productCard.data.ProductApi
import com.example.vkinternshiptask.buisness.productCard.data.ProductRepositoryImpl
import com.example.vkinternshiptask.buisness.productCard.domain.ProductRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ProductModule {

    @Provides
    @Singleton
    fun provideProductRepository(api: ProductApi): ProductRepository {
        return ProductRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit): ProductApi {
        return retrofit.create(ProductApi::class.java)
    }
}