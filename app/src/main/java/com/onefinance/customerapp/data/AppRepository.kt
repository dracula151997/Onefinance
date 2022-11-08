package com.onefinance.customerapp.data

interface AppRepository {
    fun setShowOnBoarding(showOnBoarding: Boolean)
    val shouldShowOnBoardingNextTime: Boolean
}