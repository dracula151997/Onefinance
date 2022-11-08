package com.onefinance.customerapp.presentation.main

import androidx.lifecycle.viewModelScope
import com.onefinance.customerapp.core.presentation.base.BaseViewModel
import com.onefinance.customerapp.core.presentation.base.UiAction
import com.onefinance.customerapp.presentation.dashboard.DashboardFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {
    private val _keepSplashScreen = MutableStateFlow(true)
    val keepSplashScreen = _keepSplashScreen.asStateFlow()

    init {
        viewModelScope.launch {
            delay(3000)
            _keepSplashScreen.value = false
        }

    }

    fun navigateToTransactionHistoryScreen() {
        viewModelScope.launch {
            _uiAction.emit(UiAction.NavigateWithDirection(DashboardFragmentDirections.actionHomeFragmentToTransactionsFragment()))
        }
    }
}