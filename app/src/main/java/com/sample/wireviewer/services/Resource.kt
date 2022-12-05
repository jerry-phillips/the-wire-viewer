package com.sample.wireviewer.services

import androidx.annotation.StringRes

sealed class Resource<T>(
    val data: T? = null,
    val message: Int? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(@StringRes message: Int, data: T? = null) : Resource<T>(data, message)
}