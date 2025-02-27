package com.example.myapplication.data

import retrofit2.Response

sealed class DataResult<out T, out E> {
    data class Success<out T>(val data: T) : DataResult<T, Nothing>()
    data class Error<out E>(val error: Exception) : DataResult<Nothing, E>()
    data object Loading : DataResult<Nothing, Nothing>()
}

suspend inline fun <I, O> performNetworkCall(
    crossinline call: suspend () -> Response<I>,
    crossinline deserialize: (I?) -> O?
): DataResult<O, Exception> {
    return try {
        val response = call()
        if (response.isSuccessful && response.body() != null) {
            val result = deserialize(response.body())
            if (result != null) {
                DataResult.Success(result)
            } else {
                DataResult.Error(Exception())
            }
        } else {
            DataResult.Error(Exception())
        }

    } catch (e: Exception) {
        DataResult.Error(Exception())
    }
}

suspend inline fun <I, O, U> performUpdateOperation(
    crossinline call: suspend () -> Response<I>,
    crossinline deserialize: (I?) -> O?,
    crossinline saveResult: suspend (O) -> U
): DataResult<U, Exception> {
    return try {
        val response = call()
        if (response.isSuccessful && response.body() != null) {
            val result = deserialize(response.body())
            if (result != null) {
                DataResult.Success(saveResult(result))
            } else {
                DataResult.Error(Exception())
            }
        } else {
            DataResult.Error(Exception())
        }
    } catch (e: Exception) {
        DataResult.Error(Exception())
    }
}