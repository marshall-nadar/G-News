package com.adam.gnews.utils.extensions

import io.reactivex.Single

fun <T> Single<T>.doOp(operation: (entity: T) -> Unit): Single<T> {
    return map {
        operation(it)
        return@map it
    }
}