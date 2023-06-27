package com.example.recipes.data.domain.core

sealed class ErrorState : Throwable() {
    object Network : ErrorState()
    data class TransactionError(
        val code: String? = null,
        val results: List<String?>? = null,
        override val message: String? = null
    ) : ErrorState()
    object LocalError : ErrorState()
    data class NetworkError(override val message: String?, val code: Int? = -1) : ErrorState()
}