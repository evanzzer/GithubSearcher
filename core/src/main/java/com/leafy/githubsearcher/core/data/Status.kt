package com.leafy.githubsearcher.core.data

sealed class Status<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?): Status<T>(data)
    class Empty<T>: Status<T>()
    class Loading<T>(data: T? = null): Status<T>(data)
    class Error<T>(message: String, data: T? = null): Status<T>(data, message)
}
