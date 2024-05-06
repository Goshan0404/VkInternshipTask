package com.example.vkinternshiptask

data class UiState<T>(
    val isLoading: Boolean = false,
    var data: T? = null,
    val error: Error? = null
) {
    sealed class Error() {
        class RequestError(message: String): Error()
    }
}