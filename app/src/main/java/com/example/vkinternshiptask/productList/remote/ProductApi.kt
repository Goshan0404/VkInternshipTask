package com.example.vkinternshiptask.productList.remote

import com.example.vkinternshiptask.productList.remote.model.ProductRequest
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApi {

    @GET("products")
    suspend fun getProducts(@Query("limit") limit: Int = 20,
                            @Query("skip") skip: Int
                            ): Response<ProductRequest>

    @GET("products/search")
    suspend fun searchProducts(@Query("q") query: String): Response<ProductRequest>
}