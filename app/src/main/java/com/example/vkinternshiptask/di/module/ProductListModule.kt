package com.example.vkinternshiptask.di.module

import com.example.vkinternshiptask.buisness.productList.domain.ProductListRepository
import com.example.vkinternshiptask.buisness.productList.data.ProductListRepositoryImp
import com.example.vkinternshiptask.buisness.productList.data.ProductsApi
import com.example.vkinternshiptask.di.component.ProductComponent
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(subcomponents = [ProductComponent::class])
class ProductListModule {

    @Provides
    fun provideProductListRepository(productsApi: ProductsApi): ProductListRepository {
        return ProductListRepositoryImp(productsApi)
    }
    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit): ProductsApi {
        return retrofit.create(ProductsApi::class.java)
    }
}