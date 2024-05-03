package com.example.vkinternshiptask

data class UiState<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val error: Error? = null
) {
    sealed class Error(message: String) {
        class NetworkError(message: String): Error(message)
    }
}