package com.example.vkinternshiptask.di.module

import com.example.vkinternshiptask.productList.domain.ProductListRepository
import com.example.vkinternshiptask.productList.remote.ProductApi
import com.example.vkinternshiptask.productList.remote.ProductListRepositoryImp
import dagger.Module
import dagger.Provides

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideProductRepository(productApi: ProductApi): ProductListRepository {
        return ProductListRepositoryImp(productApi)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit): ProductApi {
        return retrofit.create(ProductApi::class.java)
    }
}