package com.adedom.teg.domain

sealed class Resource<out T : Any> {
    data class Success<out T : Any>(val data: T) : Resource<T>()
    data class Error(
        val throwable: Throwable,
        val tokenExpire: Boolean = false
    ) : Resource<Nothing>()
}
