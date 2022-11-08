package com.onefinance.customerapp.core.presentation.password_checker

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onefinance.customerapp.R
import com.onefinance.customerapp.core.presentation.extensions.hasDigit
import com.onefinance.customerapp.core.presentation.extensions.hasLowerCase
import com.onefinance.customerapp.core.presentation.extensions.hasSpecialChar
import com.onefinance.customerapp.core.presentation.extensions.hasUpperCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PasswordStrengthCalculator(
    private val minLength: Int = 6,
    private val mediumLength: Int = minLength + 4,
    private val strongLength: Int = mediumLength + 4,
) : TextWatcher {

    private val _strengthLevel: MutableStateFlow<StrengthLevel> = MutableStateFlow(StrengthLevel.WEAK)
    val strengthLevel = _strengthLevel.asStateFlow()

    private val _strengthColor: MutableStateFlow<Int> = MutableStateFlow(R.color.weak)
    val strengthColor = _strengthColor.asStateFlow()


    private val _lowerCase: MutableStateFlow<Int> = MutableStateFlow(0)
    val lowerCase = _lowerCase.asStateFlow()

    private val _upperCase: MutableStateFlow<Int> = MutableStateFlow(0)
    val upperCase = _upperCase.asStateFlow()

    private val _digit: MutableStateFlow<Int> = MutableStateFlow(0)
    val digit = _digit.asStateFlow()

    private val _minLength: MutableStateFlow<Int> = MutableStateFlow(0)
    val hasMinLength = _minLength.asStateFlow()

    private val _specialChar: MutableStateFlow<Int> = MutableStateFlow(0)
    val specialChar = _specialChar.asStateFlow()

    override fun afterTextChanged(p0: Editable?) {}
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    override fun onTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (char != null) {
            _lowerCase.value = if (char.hasLowerCase()) {
                1
            } else {
                0
            }
            _upperCase.value = if (char.hasUpperCase()) {
                1
            } else {
                0
            }
            _digit.value = if (char.hasDigit()) {
                1
            } else {
                0
            }
            _minLength.value = if (char.length >= minLength) {
                1
            } else 0

            _specialChar.value = if (char.hasSpecialChar()) {
                1
            } else {
                0
            }
            calculateStrength(char)
        }
    }

    private fun calculateStrength(password: CharSequence) {
        if (password.length in 0 until minLength) {
            _strengthColor.value = R.color.weak
            _strengthLevel.value = StrengthLevel.WEAK
            return
        }

        if (_lowerCase.value == 1 && _upperCase.value == 1 && _digit.value == 1 && _specialChar.value == 1) {
            when (password.length) {
                in minLength until mediumLength -> {
                    _strengthColor.value = R.color.medium
                    _strengthLevel.value = StrengthLevel.MEDIUM
                }
                in mediumLength until strongLength -> {
                    _strengthColor.value = R.color.strong
                    _strengthLevel.value = StrengthLevel.STRONG
                }
                else -> {
                    _strengthColor.value = R.color.bulletproof
                    _strengthLevel.value = StrengthLevel.BULLETPROOF
                }
            }
        }
    }


}