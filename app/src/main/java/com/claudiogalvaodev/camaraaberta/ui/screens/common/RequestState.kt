package com.claudiogalvaodev.camaraaberta.ui.screens.common

sealed class RequestState<out T : Any> {
    data object Loading : RequestState<Nothing>()
    data class Success<out T : Any>(val data: T) : RequestState<T>()
    data object Empty : RequestState<Nothing>()
    data class Error(val message: String) : RequestState<Nothing>()
}

fun <T: Any> RequestState<T>.update(function: (T) -> T): RequestState<T> {
    return if (this is RequestState.Success) {
        RequestState.Success(function(data))
    } else {
        this
    }
}