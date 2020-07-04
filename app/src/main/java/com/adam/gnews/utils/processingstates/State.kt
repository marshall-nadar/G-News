package com.adam.gnews.utils.processingstates

sealed class State<T> {
    data class Loading<T>(var loading: Boolean) : State<T>()
    data class Error<T>(val e: Throwable) : State<T>()

    companion object {
        fun <T> publishLoading(isLoading: Boolean = true): State<T> = Loading(isLoading)

        fun <T> publishError(e: Throwable): State<T> = Error(e)
    }
}