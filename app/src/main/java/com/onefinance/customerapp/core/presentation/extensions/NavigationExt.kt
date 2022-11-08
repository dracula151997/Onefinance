package com.onefinance.customerapp.core.presentation.extensions

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.onefinance.customerapp.R
import com.onefinance.customerapp.core.presentation.base.BaseFragment
import com.onefinance.customerapp.core.presentation.base.BaseViewModel

fun <V : BaseViewModel> BaseFragment<V>.navigate(@IdRes id: Int) {
    findNavController().navigate(resId = id, args = null)
}


fun <V : BaseViewModel> BaseFragment<V>.navigate(navDirections: NavDirections) {
    findNavController().safeNavigate(navDirections)
}

fun <V : BaseViewModel> BaseFragment<V>.navigateWithMainNavGraph(navDirections: NavDirections) {
    requireActivity().findNavController(R.id.nav_host_fragment)
        .safeNavigate(navDirections)
}

fun <V : BaseViewModel> BaseFragment<V>.navigateWithMainNavGraph(
    @IdRes resId: Int,
    args: Bundle?,
    navOptions: NavOptions?,
) {
    requireActivity().findNavController(R.id.main_nav_graph)
        .navigate(
            resId = resId,
            args = args,
            navOptions = navOptions
        )
}

fun <V : BaseViewModel> BaseFragment<V>.getCurrentActivityDestination(
): Int? {
    return requireActivity().findNavController(R.id.main_nav_graph)
        .currentBackStackEntry?.destination?.id
}

/*fun <V : BaseViewModel> BaseFragment<V>.navigateToLogin() {
    val builder =
        NavOptions
            .Builder()
            .setPopUpTo(getCurrentActivityDestination() ?: 0, inclusive = true)
            .setLaunchSingleTop(true)
    navigateWithActivityNavController(
        resId = R.id.loginFragment,
        args = null,
        navOptions = builder.build()
    )
}*/

fun NavController.safeNavigate(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
}