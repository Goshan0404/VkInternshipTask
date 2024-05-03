package com.example.vkinternshiptask.productList.remote.model

data class ProductRequest(
    val limit: Int,
    val productRemotes: List<ProductRemote>,
    val skip: Int,
    val total: Int
)