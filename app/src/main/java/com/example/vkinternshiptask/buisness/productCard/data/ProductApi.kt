package com.example.vkinternshiptask.buisness.productCard.data

import com.example.vkinternshiptask.buisness.ProductRemote
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: String): ProductRemote
}