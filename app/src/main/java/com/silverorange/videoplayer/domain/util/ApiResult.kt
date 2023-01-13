package com.silverorange.videoplayer.domain.util

sealed class ApiResult<T>(val data: T? = null, val errorMessage: String? = null) {
    class Success<T>(data: T?): ApiResult<T>(data)
    class Error<T>(data: T? = null, errorMessage: String?): ApiResult<T>(data, errorMessage)
}