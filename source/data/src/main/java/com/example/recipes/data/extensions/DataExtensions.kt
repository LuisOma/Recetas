package com.example.recipes.data.extensions

import com.example.recipes.data.domain.core.ErrorState
import com.example.recipes.data.domain.core.ResponseState
import com.example.recipes.data.network.NetworkSettings
import retrofit2.Response


/*---------------------------------------STRING EXTENSIONS----------------------------------------*/
fun String.validateServerResponseMessage(): Boolean = this == "success"


/*---------------------------------------INT EXTENSIONS-------------------------------------------*/
fun Int.validateServerResponseCode(): Boolean = this in 200..299
fun Int.validateRetryCount(): Boolean = this < NetworkSettings.RETRY_MAX_COUNT


/*---------------------------------------NETWORK EXT-------------------------------------------*/
fun Response<*>.getResponseError(): ResponseState.Error<Any> {
    when (this.code()) {
        in 400..499 -> {
            return ResponseState.Error(
                ErrorState.TransactionError(
                    code = "${this.code()}",
                    message = this.message().ifEmpty { "Error de transacción:\nCódigo ${this.code()}" }
                )
            )

        }
        in 500..599 -> {
            return ResponseState.Error(
                ErrorState.TransactionError(
                    code = "500",
                    message = NetworkSettings.DEFAULT_SERVER_ERROR_MESSAGE
                )
            )
        }
        else -> return ResponseState.Error(
            ErrorState.TransactionError(
                code = "500",
                message = NetworkSettings.DEFAULT_SERVER_ERROR_MESSAGE
            )
        )
    }
}

fun Response<*>.needToRefreshToken() = this.code() == 403
