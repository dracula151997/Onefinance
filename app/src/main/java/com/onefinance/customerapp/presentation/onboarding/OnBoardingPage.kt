package com.onefinance.customerapp.presentation.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class OnBoardingPage(
    @StringRes val title: Int,
    @StringRes val description: Int,
    @StringRes val subDescription: Int,
    @DrawableRes val image: Int,
)