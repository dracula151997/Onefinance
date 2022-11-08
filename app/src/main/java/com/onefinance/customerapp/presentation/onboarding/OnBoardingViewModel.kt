package com.onefinance.customerapp.presentation.onboarding

import androidx.lifecycle.viewModelScope
import com.onefinance.customerapp.R
import com.onefinance.customerapp.core.domain.use_cases.SaveShowOnBoardingNextTimeUseCase
import com.onefinance.customerapp.core.presentation.base.BaseViewModel
import com.onefinance.customerapp.core.presentation.base.UiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val saveShowOnBoardingNextTimeUseCase: SaveShowOnBoardingNextTimeUseCase,
) : BaseViewModel() {


    fun navigateToLoginScreen() {
        viewModelScope.launch {
            _uiAction.emit(UiAction.NavigateWithDirection(OnBoardingFragmentDirections.actionOnBoardngFragmentToLoginFragment()))
        }
    }

    fun skipOnBoarding() {
        viewModelScope.launch {
            saveShowOnBoardingNextTimeUseCase(false)
            navigateToLoginScreen()
        }
    }

    fun navigateToFirstLoginScreen() {
        viewModelScope.launch {
            _uiAction.emit(UiAction.NavigateWithDirection(OnBoardingFragmentDirections.actionOnBoardngFragmentToLoginFragment()))
        }
    }

    fun getOnBoardingData(): List<OnBoardingPage> {
        return listOf(
            OnBoardingPage(
                R.string.onboarding_first_page_title,
                R.string.onboarding_first_page_description,
                R.string.onboarding_first_page_sub_description,
                R.drawable.onboarding_first_img
            ),
            OnBoardingPage(
                R.string.onboarding_second_page_title,
                R.string.onboarding_second_page_description,
                R.string.onboarding_second_page_sub_description,
                R.drawable.onboarding_second_img
            ),
            OnBoardingPage(
                R.string.onboarding_third_page_title,
                R.string.onboarding_third_page_description,
                R.string.onboarding_third_page_sub_description,
                R.drawable.onboarding_third_img
            ),

            )
    }
}