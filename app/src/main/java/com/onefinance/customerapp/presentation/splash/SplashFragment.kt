package com.onefinance.customerapp.presentation.splash

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
import com.onefinance.customerapp.core.presentation.extensions.navigate
import com.onefinance.customerapp.core.presentation.utils.ViewBindingHolder
import com.onefinance.customerapp.core.presentation.utils.ViewBindingHolderImpl
import com.onefinance.customerapp.databinding.FragmentSplashFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashViewModel>(),
    ViewBindingHolder<FragmentSplashFragmentBinding> by ViewBindingHolderImpl() {
    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        initBinding(FragmentSplashFragmentBinding.inflate(inflater, container, false), this) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.uiAction
                    .collectLatest {
                    when (it) {
                        is UiAction.NavigateWithDirection -> navigate(it.navDirections)
                        else -> {}
                    }
                }
            }
        }

    }


}