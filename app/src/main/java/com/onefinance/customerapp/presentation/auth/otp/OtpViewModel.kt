package com.onefinance.customerapp.presentation.auth.otp

import androidx.lifecycle.viewModelScope
import com.onefinance.customerapp.core.presentation.base.BaseViewModel
import com.onefinance.customerapp.core.presentation.base.UiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor() : BaseViewModel() {
    fun navigateToNewPasswordScreen() {
        viewModelScope.launch {
            _uiAction.emit(UiAction.NavigateWithDirection(OtpFragmentDirections.actionOtpFragmentToPasswordFragment()))
        }
    }
}