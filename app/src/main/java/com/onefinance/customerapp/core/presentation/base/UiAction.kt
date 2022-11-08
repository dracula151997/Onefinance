package com.onefinance.customerapp.core.presentation.base

import androidx.annotation.IdRes
import androidx.navigation.NavDirections

sealed class UiAction {
    data class Navigate(@IdRes val route: Int) : UiAction()
    data class NavigateWithDirection(val navDirections: NavDirections) : UiAction()
    data class NavigateWithParentDirection(val navDirections: NavDirections) : UiAction()
    object NavigateUp : UiAction()
}
