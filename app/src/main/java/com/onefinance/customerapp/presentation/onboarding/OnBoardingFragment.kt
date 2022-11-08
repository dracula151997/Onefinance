package com.onefinance.customerapp.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.onefinance.customerapp.core.presentation.base.BaseFragment
import com.onefinance.customerapp.core.presentation.extensions.navigate
import com.onefinance.customerapp.core.presentation.utils.ViewBindingHolder
import com.onefinance.customerapp.core.presentation.utils.ViewBindingHolderImpl
import com.onefinance.customerapp.databinding.FragmentOnBoardngBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment : BaseFragment<OnBoardingViewModel>(),
    ViewBindingHolder<FragmentOnBoardngBinding> by ViewBindingHolderImpl() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = initBinding(FragmentOnBoardngBinding.inflate(inflater, container, false), this) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireBinding {
            composeView.apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {
                    OnBoardingScreen {
                        navigate(it)
                    }
                }
            }
        }
    }

}