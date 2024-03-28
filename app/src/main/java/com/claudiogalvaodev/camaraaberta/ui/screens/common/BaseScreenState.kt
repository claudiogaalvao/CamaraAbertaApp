package com.claudiogalvaodev.camaraaberta.ui.screens.common

sealed class BaseScreenState<out T : Any> {
    data object Loading : BaseScreenState<Nothing>()
    data class Success<out T : Any>(val data: T) : BaseScreenState<T>()
    data object Empty : BaseScreenState<Nothing>()
    data class Error(val message: String) : BaseScreenState<Nothing>()
}

fun <T: Any> BaseScreenState<T>.update(function: (T) -> T): BaseScreenState<T> {
    return if (this is BaseScreenState.Success) {
        BaseScreenState.Success(function(data))
    } else {
        this
    }
}