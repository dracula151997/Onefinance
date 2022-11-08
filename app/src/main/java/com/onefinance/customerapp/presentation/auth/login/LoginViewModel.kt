package com.onefinance.customerapp.presentation.auth.login

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.onefinance.customerapp.R
import com.onefinance.customerapp.core.presentation.base.BaseViewModel
import com.onefinance.customerapp.core.presentation.base.UiAction
import com.onefinance.customerapp.core.presentation.utils.BiometricAuthListener
import com.onefinance.customerapp.core.presentation.utils.BiometricUtil
import com.onefinance.customerapp.core.presentation.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel() {
    @Inject
    lateinit var biometricUtil: BiometricUtil

    private val _isBiometricReadyForUse = MutableSharedFlow<Boolean>()
    val isBiometricReadyForAuthenticate = _isBiometricReadyForUse.asSharedFlow()


    fun performLogin(
        nationalId: String,
        password: String,
    ) {
        viewModelScope.launch {
            setLoading()
            delay(3000)
            hideLoading()
            _uiAction.emit(UiAction.NavigateWithDirection(LoginFragmentDirections.actionLoginFragmentToHomeFragment()))
        }

    }

    fun navigateToHomeScreen() {

    }

    fun showPromptBiometric(
        context: Context,
        listener: BiometricAuthListener,
    ) {
        biometricUtil.showBiometricPrompt(
            context.getString(R.string.login_biometric_dialog_title),
            context.getString(R.string.login_biometric_dialog_description),
            context.getString(R.string.login_biometric_dialog_later_button),
            listener
        )
    }

    fun navigateToForgetPasswordScreen() {
        viewModelScope.launch {
            _uiAction.emit(UiAction.NavigateWithDirection(LoginFragmentDirections.actionLoginFragmentToForgetPasswordNationalIDFragment()))
        }
    }

    fun biometricLogin() {
        viewModelScope.launch {
            val isBiometricReady = biometricUtil.isBiometricReady()
            _isBiometricReadyForUse.emit(isBiometricReady.isSuccess)
            if (!isBiometricReady.isSuccess)
                emitError(isBiometricReady.error!!)
        }
    }

    fun showBiometricError(errorCode: String, errorMessage: String) {
        if (errorCode != errorMessage) {
            viewModelScope.launch(basicExceptionHandler) {
                emitError(UiText.DynamicString(errorMessage))
            }
        }
    }


}