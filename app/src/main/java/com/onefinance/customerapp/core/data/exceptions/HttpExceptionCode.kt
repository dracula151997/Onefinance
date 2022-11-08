package com.onefinance.customerapp.core.data.exceptions

sealed class HttpExceptionCode constructor(val code: Int) {
    object Unauthorized : HttpExceptionCode(401)
    object Forbidden : HttpExceptionCode(403)
    object NotFound : HttpExceptionCode(404)
    object TimeOutError : HttpExceptionCode(408)
    object InternalServerError : HttpExceptionCode(500)
    object ServiceUnavailable : HttpExceptionCode(503)
}
