package com.example.vkinternshiptask.buisness

data class ProductRemote(
    val brand: String,
    val category: String,
    val description: String,
    val discountPercentage: Double,
    val id: Int,
    val images: List<String>,
    val price: Int,
    val rating: Double,
    val stock: Int,
    val thumbnail: String,
    val title: String
) {
    fun toProduct(): Product {
        return Product(
         brand, category, description, discountPercentage, id, images, price, rating, stock, thumbnail, title)
    }
}