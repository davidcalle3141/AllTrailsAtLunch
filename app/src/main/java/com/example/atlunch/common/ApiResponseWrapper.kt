package com.example.atlunch.common

sealed class ApiResponseWrapper<out T : Any> {
    data class Success<out T : Any>(val data: T) : ApiResponseWrapper<T>()
    data class Error(val exception: Exception) : ApiResponseWrapper<Nothing>()
    object Loading : ApiResponseWrapper<Nothing>()
}