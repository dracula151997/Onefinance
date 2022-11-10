package com.onefinance.customerapp.presentation.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.onefinance.customerapp.R
import com.onefinance.customerapp.core.presentation.base.BaseFragment
import com.onefinance.customerapp.core.presentation.base.UiAction
import com.onefinance.customerapp.core.presentation.extensions.bindViewModel
import com.onefinance.customerapp.core.presentation.extensions.navigate
import com.onefinance.customerapp.core.presentation.utils.BiometricAuthListener
import com.onefinance.customerapp.core.presentation.utils.ViewBindingHolder
import com.onefinance.customerapp.core.presentation.utils.ViewBindingHolderImpl
import com.onefinance.customerapp.core.presentation.views.LoadingDialog
import com.onefinance.customerapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginViewModel>(),
    ViewBindingHolder<FragmentLoginBinding> by ViewBindingHolderImpl() {
    private val viewModel by viewModels<LoginViewModel>()

    @Inject
    lateinit var loadingDialog: LoadingDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = initBinding(FragmentLoginBinding.inflate(inflater, container, false), this) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel(viewModel)
        subscribeObservers()
        setViewListeners()
    }

    private fun setViewListeners() {
        requireBinding {
            forgetPasswordTxt.setOnClickListener { viewModel.navigateToForgetPasswordScreen() }
            signInBtn.setOnClickListener {
                viewModel.performLogin(
                    nationalIdField.editText?.text.toString(),
                    passwordField.editText?.text.toString(),
                )
            }
            biometricBtn.setOnClickListener {
                viewModel.biometricLogin()
            }

        }
    }

    private fun subscribeObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiAction.collectLatest {
                        when (it) {
                            is UiAction.NavigateWithDirection -> navigate(it.navDirections)
                            else -> {}
                        }
                    }
                }

                launch {
                    viewModel.isBiometricReadyForAuthenticate.collectLatest {
                        if (it) {
                            viewModel.showPromptBiometric(requireContext(),
                                object : BiometricAuthListener {
                                    override fun onBiometricAuthenticationSuccess(result: BiometricPrompt.AuthenticationResult) {
                                        viewModel.biometricLogin()

                                    }

                                    override fun onBiometricAuthenticationError(
                                        errorCode: Int,
                                        errorMessage: String,
                                    ) {
                                        viewModel.showBiometricError(
                                            getString(R.string.login_biometric_dialog_later_button),
                                            errorMessage,
                                        )
                                    }

                                })
                        }
                    }
                }

                launch {
                    viewModel.nationalIdError.collectLatest {
                        if (it) {
                            binding?.nationalIdField?.setError(getString(R.string.err_manatory))
                        }
                    }
                }

                launch {
                    viewModel.passwordError.collectLatest {
                        if (it) {
                            binding?.passwordField?.setError(getString(R.string.err_manatory))
                        }
                    }
                }

            }
        }
    }

}