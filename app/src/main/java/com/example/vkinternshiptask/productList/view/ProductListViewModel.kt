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
    private val context: Application
) : ViewModel() {
    private val _uiState: MutableState<UiState<List<Product>>> = mutableStateOf(UiState())
    val uiState: State<UiState<List<Product>>> = _uiState

    private val productDomainResource = MutableSharedFlow<Resources<List<Product>>>()

    val networkObserver = NetworkConnectionObserver(context)
    val observer = networkObserver.observe()


    init {
        viewModelScope.launch {
            observer.collect {
                if (it == ConnectivityObserver.Status.Available)
                    updateProductResource(repository)
            }
        }

        viewModelScope.launch {

            productDomainResource.collect {
                when (it) {
                    is Resources.Success -> {
                        _uiState.value = UiState(data = it.data ?: emptyList())
                    }

                    is Resources.Error -> {


                        _uiState.value = UiState(error = UiState.Error.NetworkError(it.message))
                    }

                }

            }
        }
    }

    private suspend fun updateProductResource(repository: ProductListRepository) {
        productDomainResource.emit(repository.getProducts(0))
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
