package com.onefinance.customerapp.presentation.auth.forget

import androidx.lifecycle.viewModelScope
import com.onefinance.customerapp.core.presentation.base.BaseViewModel
import com.onefinance.customerapp.core.presentation.base.UiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor() : BaseViewModel() {
    fun navigateToOtp() {
        viewModelScope.launch {
            _uiAction.emit(UiAction.NavigateWithDirection(ForgetPasswordNationalIDFragmentDirections.actionForgetPasswordNationalIDFragmentToOtpFragment()))
        }
    }
}