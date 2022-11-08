package com.onefinance.customerapp.core.presentation.base

import com.onefinance.customerapp.R
import com.onefinance.customerapp.core.data.exceptions.StandardErrors
import com.onefinance.customerapp.core.presentation.utils.UiText
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

@Suppress("FunctionName")
inline fun BaseCoroutineExceptionHandler(crossinline handler: (CoroutineContext, UiText, Throwable) -> Unit): CoroutineExceptionHandler =
    object : AbstractCoroutineContextElement(CoroutineExceptionHandler), CoroutineExceptionHandler {
        override fun handleException(context: CoroutineContext, exception: Throwable) {
            val uiText = checkException(exception)
            handler(context, uiText, exception)
        }
    }

@Suppress("FunctionName")
fun checkException(exception: Throwable): UiText {
    exception.printStackTrace()
    return when (exception) {
        is StandardErrors -> {
            when (exception) {
                StandardErrors.InternetError -> UiText.StringResource(R.string.internet_connection_error)
                StandardErrors.UnknownError -> UiText.StringResource(R.string.backend_error_0)
                StandardErrors.UnAuthorize -> UiText.StringResource(R.string.client_unauthorized_error)
            }
        }
//        is BackendExceptions -> {
//            UiText.StringResource(exception.errorRes)
//        }
        else -> UiText.DynamicString(exception.message ?: "")
    }
}