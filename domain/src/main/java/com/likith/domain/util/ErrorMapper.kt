package com.likith.domain.util

interface ErrorMapper {
    fun fromThrowable(t: Throwable): DataError.Remote
    fun fromHttp(code: Int): DataError.Remote
}