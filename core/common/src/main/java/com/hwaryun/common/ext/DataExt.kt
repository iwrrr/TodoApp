package com.hwaryun.common.ext

import com.hwaryun.common.ConnectivityException
import com.hwaryun.common.DataResult
import com.hwaryun.common.NetworkClientException
import com.hwaryun.common.UnexpectedValuesRepresentation
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

suspend fun <T> execute(block: suspend () -> T): DataResult<T> {
    return try {
        DataResult.Success(block.invoke())
    } catch (throwable: Throwable) {
        Timber.e(throwable, "ERROR ====> $throwable")

        when (throwable) {
            /**
             * Handle http status code can reference to
             * https://developer.mozilla.org/en-US/docs/Web/HTTP/Status#client_error_responses
             * */
            is HttpException -> {
                when (throwable.code()) {
                    in 300..399 -> {
                        DataResult.Failure(NetworkClientException("Redirect"))
                    }

                    in 400..499 -> {
                        DataResult.Failure(NetworkClientException(throwable.response()?.message()))
                    }

                    in 500..599 -> {
                        DataResult.Failure(NetworkClientException("Server Error"))
                    }

                    else -> {
                        DataResult.Failure(UnexpectedValuesRepresentation())
                    }
                }
            }

            is IOException -> {
                DataResult.Failure(ConnectivityException())
            }

            else -> {
                DataResult.Failure(throwable)
            }
        }
    }
}

suspend fun <T> proceed(block: suspend () -> T): DataResult<T> {
    return try {
        DataResult.Success(block.invoke())
    } catch (throwable: Throwable) {
        throwable.printStackTrace()
        DataResult.Failure(throwable)
    }
}