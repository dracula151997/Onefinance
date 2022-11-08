package com.onefinance.customerapp.core.presentation.extensions

import android.view.Menu
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


/**
 * Issue Link: https://github.com/android/architecture-components-samples/issues/1003
 *
 * https://github.com/android/architecture-components-samples/issues/1003#issuecomment-881210864
 */
fun BottomNavigationView.setupBottomNavigationWithNavController(navController: NavController?) {
    navController?.let {
        this.setupWithNavController(it)
    }

    NavigationUI

    /**
     *  Fixed by
     *     NavOptions setRestoreState(false) with  setPopUpTo(saveState = true)
     *     or
     *     NavOptions setRestoreState(true) with  setPopUpTo(saveState = false)
     *
     *  @see [NavigationUI.onNavDestinationSelected]
     */
    this.setOnItemSelectedListener { item ->

        val builder = NavOptions.Builder().setLaunchSingleTop(true).setRestoreState(true)
        if (
            navController!!.currentDestination!!.parent!!.findNode(item.itemId)
                    is ActivityNavigator.Destination
        ) {
            builder.setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
                .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
                .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
                .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)
        } else {
            builder.setEnterAnim(androidx.navigation.ui.R.animator.nav_default_enter_anim)
                .setExitAnim(androidx.navigation.ui.R.animator.nav_default_exit_anim)
                .setPopEnterAnim(androidx.navigation.ui.R.animator.nav_default_pop_enter_anim)
                .setPopExitAnim(androidx.navigation.ui.R.animator.nav_default_pop_exit_anim)
        }
        if (item.order and Menu.CATEGORY_SECONDARY == 0) {
            builder.setPopUpTo(
                navController.graph.findStartDestination().id,
                inclusive = false,
                saveState = false
            )
        }
        val options = builder.build()
        return@setOnItemSelectedListener try {
            navController.navigate(item.itemId, null, options)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}