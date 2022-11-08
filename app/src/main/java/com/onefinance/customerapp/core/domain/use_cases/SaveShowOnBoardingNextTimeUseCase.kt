package com.onefinance.customerapp.core.domain.use_cases

import com.onefinance.customerapp.data.AppRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class SaveShowOnBoardingNextTimeUseCase @Inject constructor(
    private val appRepository: AppRepository,
) {
    operator fun invoke(showNextTime: Boolean) {
        appRepository.setShowOnBoarding(showNextTime)
    }
}