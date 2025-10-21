package com.likith.presentation.util

import androidx.annotation.StringRes
import com.likith.domain.util.DataError
import com.likith.presentation.R

@StringRes
fun DataError.toMessageRes(): Int = when (this) {
    DataError.Remote.BAD_REQUEST -> R.string.err_bad_request
    DataError.Remote.REQUEST_TIMEOUT -> R.string.err_request_timeout
    DataError.Remote.UNAUTHORIZED -> R.string.err_unauthorized
    DataError.Remote.FORBIDDEN -> R.string.err_forbidden
    DataError.Remote.NOT_FOUND -> R.string.err_not_found
    DataError.Remote.TOO_MANY_REQUESTS -> R.string.err_too_many_requests
    DataError.Remote.NO_INTERNET -> R.string.err_no_internet
    DataError.Remote.SERVER_ERROR -> R.string.err_server_error
    DataError.Remote.SERVICE_UNAVAILABLE -> R.string.err_service_unavailable
    DataError.Remote.SERIALIZATION -> R.string.err_serialization
    DataError.Remote.UNKNOWN -> R.string.err_unknown
    DataError.Local.IMAGE_LOAD_FAILED -> R.string.err_image_loading
    DataError.Local.UNKNOWN -> R.string.err_unknown

}