package com.likith.data.util

import com.likith.domain.util.DataError
import com.likith.domain.util.ErrorMapper

class DefaultErrorMapper : ErrorMapper {
    override fun fromThrowable(t: Throwable): DataError.Remote = when (t) {
        is com.google.gson.JsonParseException -> DataError.Remote.SERIALIZATION
        is java.net.SocketTimeoutException   -> DataError.Remote.REQUEST_TIMEOUT
        is java.io.IOException               -> DataError.Remote.NETWORK
        is retrofit2.HttpException           -> fromHttp(t.code())
        else                                 -> DataError.Remote.UNKNOWN
    }

    override fun fromHttp(code: Int): DataError.Remote = when (code) {
        400 -> DataError.Remote.BAD_REQUEST
        401 -> DataError.Remote.UNAUTHORIZED
        403 -> DataError.Remote.FORBIDDEN
        404 -> DataError.Remote.NOT_FOUND
        408 -> DataError.Remote.REQUEST_TIMEOUT
        429 -> DataError.Remote.TOO_MANY_REQUESTS
        500 -> DataError.Remote.SERVER_ERROR
        503 -> DataError.Remote.SERVICE_UNAVAILABLE
        else -> DataError.Remote.UNKNOWN
    }
}