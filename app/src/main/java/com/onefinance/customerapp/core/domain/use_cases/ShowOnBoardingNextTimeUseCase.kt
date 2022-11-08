package com.onefinance.customerapp.core.domain.use_cases

import com.onefinance.customerapp.data.AppRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ShowOnBoardingNextTimeUseCase @Inject constructor(
    private val appRepository: AppRepository
) {
    operator fun invoke(): Boolean {
        return appRepository.shouldShowOnBoardingNextTime
    }
}