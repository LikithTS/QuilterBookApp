package com.likith.domain.util

sealed interface DataError: Error {

    /**
     * We can extend remote error based on requirement
     */
    enum class Remote : DataError {
        BAD_REQUEST,
        REQUEST_TIMEOUT,
        UNAUTHORIZED,
        FORBIDDEN,
        NOT_FOUND,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER_ERROR,
        SERVICE_UNAVAILABLE,
        SERIALIZATION,
        UNKNOWN
    }

    /**
     * We can extend local error based on app usecase
     */
    enum class Local : DataError {
        IMAGE_LOAD_FAILED,
        UNKNOWN
    }

}