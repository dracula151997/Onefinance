package com.onefinance.customerapp.presentation.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.onefinance.customerapp.R

sealed class BottomNavItem(
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
    val route: String,
) {
    object Home : BottomNavItem(
        R.drawable.ic_home_selected,
        R.drawable.ic_home_unselected,
        "home"
    )

    object Card : BottomNavItem(
        R.drawable.ic_card_selected,
        R.drawable.ic_card_unselected,
        "card"
    )

    object Offers : BottomNavItem(
        R.drawable.ic_offer_selected,
        R.drawable.ic_offer_unselected,
        "offers"
    )

    object Calculator : BottomNavItem(
        R.drawable.ic_card_selected,
        R.drawable.ic_calculator_unselected,
        "calculator"
    )
}

sealed class DrawerNavScreen(
    @StringRes val label: Int,
    @DrawableRes val icon: Int,
    val route: String,
) {
    object Installments : DrawerNavScreen(
        R.string.drawer_nav_installments_title,
        R.drawable.ic_installments,
        "installments"
    )

    object TransactionHistory : DrawerNavScreen(
        R.string.drawer_nav_transaction_history_title,
        R.drawable.ic_transaction_history,
        "transaction_history"
    )

    object OurBranches : DrawerNavScreen(
        R.string.drawer_nav_our_branches_title,
        R.drawable.ic_our_branches,
        "our_branches"
    )

    object ContactUs : DrawerNavScreen(
        R.string.drawer_nav_contact_us_title,
        R.drawable.ic_transaction_history,
        "contact_us"
    )

    object FAQs : DrawerNavScreen(
        R.string.drawer_nav_faqs_title,
        R.drawable.ic_faqs,
        "transaction_history"
    )

    object TermsAndCondition : DrawerNavScreen(
        R.string.drawer_nav_terms_and_conditions_title,
        R.drawable.ic_terms_and_conditions,
        "transaction_history"
    )

    object Logout : DrawerNavScreen(
        R.string.drawer_nav_logout_title,
        R.drawable.ic_logout,
        "transaction_history"
    )
}