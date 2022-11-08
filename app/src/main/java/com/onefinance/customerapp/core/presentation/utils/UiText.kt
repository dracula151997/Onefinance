package com.onefinance.customerapp.core.presentation.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.onefinance.customerapp.R

sealed class UiText {
    data class DynamicString(val value: String) : UiText()
    data class StringResource(@StringRes val id: Int?) : UiText()

    companion object {
        fun unknownError(): UiText {
            return StringResource(
                R.string.backend_error_0
            )
        }

        fun emptyStringResource(): UiText {
            return StringResource(null)
        }
    }

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> id?.let { stringResource(id = it) } ?: ""
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> id?.let { context.getString(it) } ?: ""
        }
    }
}