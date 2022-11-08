package com.onefinance.customerapp.presentation.auth.newpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.onefinance.customerapp.R
import com.onefinance.customerapp.core.presentation.base.BaseFragment
import com.onefinance.customerapp.core.presentation.base.BaseViewModel
import com.onefinance.customerapp.core.presentation.password_checker.PasswordStrengthCalculator
import com.onefinance.customerapp.core.presentation.password_checker.StrengthLevel
import com.onefinance.customerapp.core.presentation.utils.ViewBindingHolder
import com.onefinance.customerapp.core.presentation.utils.ViewBindingHolderImpl
import com.onefinance.customerapp.databinding.FragmentPasswordBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val TAG = "PasswordFragment"

@AndroidEntryPoint
class PasswordFragment : BaseFragment<BaseViewModel>(),
    ViewBindingHolder<FragmentPasswordBinding> by ViewBindingHolderImpl() {
    private val viewModel by viewModels<NewPasswordViewModel>()
    private var color: Int = R.color.weak


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = initBinding(FragmentPasswordBinding.inflate(inflater, container, false), this) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireBinding {
            val passwordStrengthCalculator = PasswordStrengthCalculator()
            newPasswordEt.editText?.addTextChangedListener(passwordStrengthCalculator)

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                    launch {

                        passwordStrengthCalculator.strengthLevel.collect { strengthLevel ->
                            displayStrengthLevel(strengthLevel)
                        }
                    }

                    launch {
                        passwordStrengthCalculator.strengthColor.collect { strengthColor ->
                            color = strengthColor
                        }
                    }

                    launch {
                        passwordStrengthCalculator.lowerCase.collect { value ->
                            displayPasswordSuggestions(value, lowerCaseImg, lowerCaseTxt)
                        }
                    }

                    launch {
                        passwordStrengthCalculator.upperCase.collect { value ->
                            displayPasswordSuggestions(value, upperCaseImg, upperCaseTxt)
                        }
                    }

                    launch {
                        passwordStrengthCalculator.digit.collect { value ->
                            displayPasswordSuggestions(value, digitImg, digitTxt)
                        }
                    }

                    launch {
                        passwordStrengthCalculator.specialChar.collect { value ->
                            displayPasswordSuggestions(value, specialCharImg, specialCharTxt)
                        }
                    }

                    launch {
                        passwordStrengthCalculator.strengthLevel.collect { strengthLevel ->
                            displayStrengthLevel(strengthLevel)
                        }
                    }

                    launch {
                        passwordStrengthCalculator.hasMinLength.collect {
                            displayPasswordSuggestions(value = it,
                                minLengthImg,
                                minLengthTxt)
                        }

                    }
                }
            }












        }
    }

    private fun displayPasswordSuggestions(value: Int, imageView: ImageView, textView: TextView) {
        if (value == 1) {
            imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.bulletproof))
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.bulletproof))
        } else {
            imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.spanish_gray))
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.spanish_gray))
        }
    }

    private fun displayStrengthLevel(strengthLevel: StrengthLevel) {
        requireBinding {
            changePasswordBtn.isEnabled = strengthLevel != StrengthLevel.WEAK
        }
//        button.isEnabled = strengthLevel == StrengthLevel.BULLETPROOF
//
//        strength_level_txt.text = strengthLevel.name
//        strength_level_txt.setTextColor(ContextCompat.getColor(this, color))
//        strength_level_indicator.setBackgroundColor(ContextCompat.getColor(this, color))
    }
}