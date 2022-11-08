package com.onefinance.customerapp.core.presentation.utils

import androidx.annotation.StringRes

data class Biometric(
    @StringRes val error: Int? = null,
    val isSuccess: Boolean,
)
