package com.onefinance.customerapp.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.onefinance.customerapp.core.SharedPrefKeys.SHOW_ONBOARDING
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : AppRepository {
    override fun setShowOnBoarding(showOnBoarding: Boolean) {
        sharedPreferences.edit {
            putBoolean(SHOW_ONBOARDING, showOnBoarding)
        }
    }

    override val shouldShowOnBoardingNextTime: Boolean
        get() = sharedPreferences.getBoolean(SHOW_ONBOARDING, true)

}