package com.onefinance.customerapp.core.presentation.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.onefinance.customerapp.R
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class BiometricUtil @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private fun hasBiometricCapability(): Int {
        val biometricManager = BiometricManager.from(context)
        return biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_WEAK
                    or BiometricManager.Authenticators.BIOMETRIC_STRONG
        )
    }

    fun isBiometricReady(): Biometric {
        when (hasBiometricCapability()) {
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                return Biometric(
                    R.string.fingerprint_error_hw_not_available,
                    false
                )
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                return Biometric(
                    R.string.fingerprint_error_hw_not_present,
                    false
                )
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                return Biometric(
                    R.string.fingerprint_error_no_fingerprints,
                    false
                )
            }
            BiometricManager.BIOMETRIC_SUCCESS -> {
                return Biometric(0, true)
            }
            else -> {
                return Biometric(
                    R.string.backend_error_0,
                    false
                )
            }
        }
    }

    private fun setBiometricPromptInfo(
        title: String,
        description: String,
        negativeButton: String,
    ): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setDescription(description)
            .setNegativeButtonText(negativeButton)
            .setConfirmationRequired(false)
            .build()
    }

    private fun initBiometricPrompt(
        context: Context,
        listener: BiometricAuthListener,
    ): BiometricPrompt {
        val executor = ContextCompat.getMainExecutor(context)
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                listener.onBiometricAuthenticationError(errorCode, errString.toString())
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                listener.onBiometricAuthenticationSuccess(result)
            }
        }
        return BiometricPrompt(context as AppCompatActivity, executor, callback)
    }

    fun showBiometricPrompt(
        title: String,
        description: String,
        negativeButton: String,
        listener: BiometricAuthListener,
    ) {
        val promptInfo = setBiometricPromptInfo(
            title, description, negativeButton
        )
        val biometricPrompt = initBiometricPrompt(context, listener)
        biometricPrompt.apply {
            authenticate(promptInfo)
        }
    }
}

