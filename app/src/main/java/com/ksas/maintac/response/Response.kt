package com.ksas.maintac.response

sealed class Response<out T>(
    val dataText: T? = null,
    val message: String = ""
) {
    data class Success<out R>(val data: R) : Response<R>()
    data class Failure(val err: Throwable) : Response<Nothing>()
    object Loading : Response<Nothing>()
}
