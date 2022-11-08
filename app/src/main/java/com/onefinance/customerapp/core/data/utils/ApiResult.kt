package com.onefinance.customerapp.core.data.utils

import com.onefinance.customerapp.R
import com.onefinance.customerapp.core.presentation.utils.UiText
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

sealed class ApiResult<out T> {
    data class Success<T>(val data: T?) : ApiResult<T>()
    data class Exception(val exceptionMessage: UiText) : ApiResult<Nothing>()
}

fun testFlow() = flow {
    delay(2000)
    emit(ApiResult.Success("Hello"))
    delay(2000)
    emit(ApiResult.Exception(UiText.StringResource(R.string.app_name)))
    delay(1000)
}

fun <T> ApiResult<T>.onSuccess(action: (T?) -> Unit) {
    if (this is ApiResult.Success) action(this.data)
}

inline fun <T> ApiResult<T>.onFailure(crossinline action: (UiText) -> Unit) {
    if (this is ApiResult.Exception) action(this.exceptionMessage)
}