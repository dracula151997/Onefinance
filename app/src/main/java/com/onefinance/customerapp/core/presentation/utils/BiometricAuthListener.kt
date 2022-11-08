package com.onefinance.customerapp.core.presentation.utils

import androidx.biometric.BiometricPrompt

interface BiometricAuthListener {
  fun onBiometricAuthenticationSuccess(result: BiometricPrompt.AuthenticationResult)
  fun onBiometricAuthenticationError(errorCode: Int, errorMessage: String)
}
