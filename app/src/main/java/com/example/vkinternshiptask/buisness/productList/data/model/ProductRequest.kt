package com.example.vkinternshiptask.buisness.productList.data.model

import com.example.vkinternshiptask.buisness.ProductRemote


data class ProductRequest(
    val limit: Int,
    val products: List<ProductRemote>,
    val skip: Int,
    val total: Int
)