package com.onefinance.customerapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import com.onefinance.customerapp.core.presentation.base.BaseFragment
import com.onefinance.customerapp.core.presentation.base.BaseViewModel
import com.onefinance.customerapp.core.presentation.extensions.navigate
import com.onefinance.customerapp.presentation.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<BaseViewModel>() {
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            HomeScreen(viewModel, onNavigateToNextScreen = { navigate(it) })
        }
    }

}


