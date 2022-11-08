package com.onefinance.customerapp.presentation.auth.forget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.onefinance.customerapp.core.presentation.base.BaseFragment
import com.onefinance.customerapp.core.presentation.base.UiAction
import com.onefinance.customerapp.core.presentation.extensions.bindViewModel
import com.onefinance.customerapp.core.presentation.extensions.navigate
import com.onefinance.customerapp.core.presentation.utils.ViewBindingHolder
import com.onefinance.customerapp.core.presentation.utils.ViewBindingHolderImpl
import com.onefinance.customerapp.databinding.FragmentForgetPasswordNationalIdBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForgetPasswordNationalIDFragment : BaseFragment<ForgetPasswordViewModel>(),
    ViewBindingHolder<FragmentForgetPasswordNationalIdBinding> by ViewBindingHolderImpl() {
    private val viewModel by viewModels<ForgetPasswordViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View =
        initBinding(FragmentForgetPasswordNationalIdBinding.inflate(inflater, container, false),
            this) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel(viewModel)
        requireBinding {
            nextBtn.setOnClickListener {
                viewModel.navigateToOtp()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiAction.collectLatest {
                        when (it) {
                            is UiAction.NavigateWithDirection -> navigate(it.navDirections)
                            else -> TODO()
                        }
                    }
                }
            }
        }
    }
}