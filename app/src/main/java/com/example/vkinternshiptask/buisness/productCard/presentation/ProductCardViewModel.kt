package com.example.vkinternshiptask.buisness.productCard.presentation

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkinternshiptask.buisness.Product
import com.example.vkinternshiptask.buisness.Resources
import com.example.vkinternshiptask.buisness.UiState
import com.example.vkinternshiptask.buisness.productCard.domain.ProductRepository
import com.example.vkinternshiptask.buisness.productList.domain.ProductListRepository
import com.example.vkinternshiptask.buisness.productList.presentation.ProductListViewModel
import com.example.vkinternshiptask.di.component.ScreenScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ScreenScope
class ProductCardViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val id: String
) :
    ViewModel() {
    private val _uiState: MutableState<UiState<Product>> = mutableStateOf(UiState())
    val uiState: State<UiState<Product>> = _uiState

    private val productState = MutableSharedFlow<Resources<Product>>()

    init {
        viewModelScope.launch {
            getProduct(id)
        }

        viewModelScope.launch {
            productState.collect {
                when (it) {
                    is Resources.Error -> _uiState.value =
                        UiState(error = UiState.Error.RequestError(it.message))

                    is Resources.Success ->
                        _uiState.value = UiState(data = it.data)
                }
            }
        }
    }

    private suspend fun getProduct(id: String) {
        _uiState.value = UiState(isLoading = true)
        productState.emit(repository.getProduct(id))
    }

    class ProductViewModelFactory @AssistedInject constructor(
        private val repository: ProductRepository,
        @Assisted private val id: String
    ) :
        AbstractSavedStateViewModelFactory() {
        override fun <T : ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            return ProductCardViewModel(
                repository,
                id
            ) as T
        }
    }
}