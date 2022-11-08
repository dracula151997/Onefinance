package com.onefinance.customerapp.presentation.splash

import androidx.lifecycle.viewModelScope
import com.onefinance.customerapp.core.domain.use_cases.ShowOnBoardingNextTimeUseCase
import com.onefinance.customerapp.core.presentation.base.BaseViewModel
import com.onefinance.customerapp.core.presentation.base.UiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getShowOnBoardingNextTimeUseCase: ShowOnBoardingNextTimeUseCase,
) : BaseViewModel() {

    init {
        viewModelScope.launch {
            delay(1500)
            shouldShowOnBoarding()
        }
    }

    private fun shouldShowOnBoarding() {
        viewModelScope.launch {
            if (getShowOnBoardingNextTimeUseCase()) {
                navigateToOnBoarding()
            } else {
                navigateToLogin()
            }
        }

    }

    private suspend fun navigateToOnBoarding() {
        _uiAction.emit(UiAction.NavigateWithDirection(SplashFragmentDirections.actionSplashFragmentFragmentToOnBoardngFragment()))
    }

    private suspend fun navigateToLogin() {
        _uiAction.emit(UiAction.NavigateWithDirection(SplashFragmentDirections.actionSplashFragmentFragmentToLoginFragment()))

    }
}