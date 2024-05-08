package com.example.vkinternshiptask.buisness.productList.data

import com.example.vkinternshiptask.buisness.productList.data.model.ProductRequest
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsApi {

    @GET("products")
    suspend fun getProducts(@Query("limit") limit: Int = 20,
                            @Query("skip") skip: Int
                            ): Response<ProductRequest>

    @GET("products/search")
    suspend fun searchProducts(@Query("q") query: String): Response<ProductRequest>
}