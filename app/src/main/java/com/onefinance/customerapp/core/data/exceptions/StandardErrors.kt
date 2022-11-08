package com.onefinance.customerapp.core.data.exceptions

sealed class StandardErrors : Throwable() {
    object UnknownError : StandardErrors()
    object InternetError : StandardErrors()
    object UnAuthorize : StandardErrors()
}
