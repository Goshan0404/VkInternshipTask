package com.example.vkinternshiptask.productList.view

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkfuture.utils.network.ConnectivityObserver
import com.example.vkfuture.utils.network.NetworkConnectionObserver
import com.example.vkinternshiptask.Resources
import com.example.vkinternshiptask.UiState
import com.example.vkinternshiptask.productList.domain.ProductListRepository
import com.example.vkinternshiptask.productList.domain.model.Product
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class ProductListViewModel @Inject constructor(
    private val repository: ProductListRepository,
    context: Application
) : ViewModel() {
    private val _uiState: MutableState<UiState<MutableList<Product>>> = mutableStateOf(UiState())
    val uiState: State<UiState<MutableList<Product>>> = _uiState

    private val productState = MutableSharedFlow<Resources<List<Product>>>()

    private val networkObserver = NetworkConnectionObserver(context)
    private val observer = networkObserver.observe()


    init {
        viewModelScope.launch {
            observer.collect {
                if (it == ConnectivityObserver.Status.Available)
                    getProducts()
            }
        }

        viewModelScope.launch {

            productState.collect {
                when (it) {
                    is Resources.Success -> {
                        _uiState.value = UiState(data = it.data?.toMutableList() ?: mutableListOf())
                    }

                    is Resources.Error -> {
                        _uiState.value = UiState(error = UiState.Error.RequestError(it.message))
                    }

                }

            }
        }
    }


    fun getProducts(query: String = "", skip: Int = 0) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            if (query.isBlank())
                productState.emit(repository.getProducts(skip))
            else
                productState.emit(repository.searchProducts(query))
        }
    }


    class ProductsListSavedStateViewModelFactory @AssistedInject constructor(
        private val repository: ProductListRepository,
        @Assisted private val context: Application
    ) :
        AbstractSavedStateViewModelFactory() {
        override fun <T : ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            return ProductListViewModel(
                repository,
                context
            ) as T
        }
    }
}
