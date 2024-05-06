package com.example.vkinternshiptask.productList.remote

import android.util.Log
import com.example.vkinternshiptask.Resources
import com.example.vkinternshiptask.productList.domain.ProductListRepository
import com.example.vkinternshiptask.productList.domain.model.Product
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ProductListRepositoryImp @Inject constructor(private val productApi: ProductApi) :
    ProductListRepository {

    val handler = CoroutineExceptionHandler { _, exception ->
        Log.d("Network", "Caught $exception")
    }

    override suspend fun getProducts(skip: Int): Resources<List<Product>> {

        return try {
            val response = productApi.getProducts(skip = skip)

            if (response.isSuccessful) {
                Resources.Success(
                    response.body()?.products?.map { it.toProduct() }
                        ?: listOf())
            } else
                Resources.Error(message = response.message())
        } catch (e: Exception) {
            Resources.Error(message = "Network Exception")
        }
    }

    override suspend fun searchProducts(query: String): Resources<List<Product>> {
        return try {
            val response = productApi.searchProducts(query)

            if (response.isSuccessful) {
                Resources.Success(
                    response.body()?.products?.map { it.toProduct() }
                        ?: listOf())
            } else
                Resources.Error(message = response.message())
        } catch (e: Exception) {
            Resources.Error(message = "Network Exception")
        }
    }
}