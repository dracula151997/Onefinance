package com.onefinance.customerapp.presentation.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.onefinance.customerapp.core.presentation.base.BaseViewModel
import com.onefinance.customerapp.core.presentation.base.UiAction
import com.onefinance.customerapp.presentation.dashboard.DashboardFragmentDirections
import com.onefinance.customerapp.presentation.dashboard.components.BottomNavItem
import com.onefinance.customerapp.presentation.dashboard.components.DrawerNavScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {
    private val _keepSplashScreen = MutableStateFlow(true)
    val keepSplashScreen = _keepSplashScreen.asStateFlow()

    val drawerItems by mutableStateOf(
        listOf(
            DrawerNavScreen.Installments,
            DrawerNavScreen.TransactionHistory,
            DrawerNavScreen.OurBranches,
            DrawerNavScreen.ContactUs,
            DrawerNavScreen.FAQs,
            DrawerNavScreen.TermsAndCondition,
            DrawerNavScreen.Logout

        )
    )

    val bottomNavigationItems by mutableStateOf(
        listOf(
            BottomNavItem.Home, BottomNavItem.Card, BottomNavItem.Offers, BottomNavItem.Calculator
        )
    )

    fun navigateToTransactionHistoryScreen() {
        viewModelScope.launch {
            _uiAction.emit(UiAction.NavigateWithDirection(DashboardFragmentDirections.actionHomeFragmentToTransactionsFragment()))
        }
    }

    fun navigateToTransactionDetails(id: Int) {
        viewModelScope.launch {

        }
    }

    fun navigateToAllTransactionsScreen() {
        viewModelScope.launch {
            _uiAction.emit(
                UiAction.NavigateWithDirection(
                    DashboardFragmentDirections.actionHomeFragmentToTransactionsFragment()
                )
            )
        }
    }
}