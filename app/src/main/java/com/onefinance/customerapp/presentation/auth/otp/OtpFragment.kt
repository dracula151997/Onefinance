package com.onefinance.customerapp.presentation.auth.otp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.onefinance.customerapp.core.presentation.base.BaseFragment
import com.onefinance.customerapp.core.presentation.base.UiAction
import com.onefinance.customerapp.core.presentation.extensions.collectInLifeCycle
import com.onefinance.customerapp.core.presentation.extensions.navigate
import com.onefinance.customerapp.core.presentation.utils.ViewBindingHolder
import com.onefinance.customerapp.core.presentation.utils.ViewBindingHolderImpl
import com.onefinance.customerapp.databinding.FragmentOtpBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OtpFragment : BaseFragment<OtpViewModel>(),
    ViewBindingHolder<FragmentOtpBinding> by ViewBindingHolderImpl() {
    private val viewModel by viewModels<OtpViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = initBinding(FragmentOtpBinding.inflate(inflater, container, false), this) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireBinding {
            nextBtn.setOnClickListener { viewModel.navigateToNewPasswordScreen() }
        }
        collectInLifeCycle {
            launch {
                viewModel.uiAction.collectLatest {
                    when (it) {
                        is UiAction.NavigateWithDirection -> navigate(it.navDirections)
                        else -> {}
                    }
                }
            }
        }
    }
}