package com.onefinance.customerapp.presentation.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.onefinance.customerapp.R
import com.onefinance.customerapp.core.presentation.base.UiAction
import com.onefinance.customerapp.presentation.dashboard.components.BottomNavItem
import com.onefinance.customerapp.presentation.dashboard.components.DrawerContent
import com.onefinance.customerapp.presentation.dashboard.components.DrawerNavScreen
import com.onefinance.customerapp.presentation.dashboard.home.HomeScreen
import com.onefinance.customerapp.presentation.main.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun DashboardScreen(
    viewModel: MainViewModel = hiltViewModel(),
    onNavigateToNextScreen: (NavDirections) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = Unit) {
        lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiAction.collectLatest {
                    when (it) {
                        is UiAction.NavigateWithDirection -> onNavigateToNextScreen(it.navDirections)
                        else -> {}
                    }
                }

            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = colorResource(id = R.color.home_screen_background),
        topBar = {
            BaseTopAppBar(
                backgroundColor = if (currentRoute == BottomNavItem.Home.route) colorResource(
                    id = R.color.drawer_layout_color
                ) else colorResource(
                    id = R.color.home_screen_background
                ),
                onNavigationDrawerClicked = {
                    scope.launch { scaffoldState.drawerState.open() }
                },
                onNotificationClicked = {},
                onProfileClicked = {},
            )

        },
        bottomBar = {
            HomeBottomNavigation(
                currentRoute = currentRoute, onBottomNavigationItemClicked = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                }, items = viewModel.bottomNavigationItems
            )
        },
        drawerContent = {
            DrawerContent(viewModel.drawerItems) { route ->
                scope.launch {
                    scaffoldState.drawerState.close()
                    when (route) {
                        DrawerNavScreen.TransactionHistory.route -> viewModel.navigateToTransactionHistoryScreen()
                        else -> {
                            navController.navigate(route = route) {
                                popUpTo(navController.graph.findStartDestination().id)
                                launchSingleTop = true
                            }
                        }
                    }
                }

            }


        },
        drawerBackgroundColor = colorResource(id = R.color.drawer_layout_color),
        drawerShape = RoundedCornerShape(topEnd = 30.dp),
        content = { contentPadding ->
            DashboardNavHost(navController, Modifier.padding(contentPadding), onSeeAllClicked = {
                viewModel.navigateToAllTransactionsScreen()
            }, onRecentlyTransactionClicked = { id ->
                viewModel.navigateToTransactionDetails(id)
            })
        },
    )
}

@Composable
private fun DashboardNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onSeeAllClicked: () -> Unit,
    onRecentlyTransactionClicked: (id: Int) -> Unit,
) {
    NavHost(
        navController = navController, startDestination = BottomNavItem.Home.route, modifier
    ) {
        composable(BottomNavItem.Home.route) {
            HomeScreen(onSeeAllClicked, onRecentlyTransactionClicked)
        }
        composable(BottomNavItem.Calculator.route) {
            Text(text = "Calculator")
        }
        composable(BottomNavItem.Card.route) {
            Text(text = "Card")
        }
        composable(BottomNavItem.Offers.route) {
            Text(text = "Offers")
        }
        composable(DrawerNavScreen.Installments.route) {
            Text(text = "Installments")
        }
        composable(DrawerNavScreen.TransactionHistory.route) {
            Text(text = "Transaction History")
        }
        composable(DrawerNavScreen.OurBranches.route) {
            OurBranchesContent()
        }
    }
}

@Composable
fun OurBranchesContent() {

}

@Composable
fun BaseTopAppBar(
    backgroundColor: Color = colorResource(id = R.color.home_screen_background),
    onNotificationClicked: () -> Unit,
    onProfileClicked: () -> Unit,
    onNavigationDrawerClicked: () -> Unit,
) {
    TopAppBar(
        backgroundColor = backgroundColor,
        elevation = 0.dp,
        contentPadding = PaddingValues(horizontal = 30.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.ic_drawer_nav_icon),
                contentDescription = null,
                modifier = Modifier
                    .clickable(
                        onClick = onNavigationDrawerClicked, interactionSource = remember {
                            MutableInteractionSource()
                        }, indication = null
                    )
                    .weight(1f),
                alignment = Alignment.TopStart
            )
            Image(
                painter = painterResource(id = R.drawable.ic_notifications),
                contentDescription = null,
                modifier = Modifier.clickable(
                    onClick = onNotificationClicked, interactionSource = remember {
                        MutableInteractionSource()
                    }, indication = null
                )
            )
            Spacer(modifier = Modifier.width(12.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = null,
                modifier = Modifier.clickable(
                    onClick = onProfileClicked, interactionSource = remember {
                        MutableInteractionSource()
                    }, indication = null
                )
            )

        }

    }
}

@Composable
fun BackTopBar(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
) {
    Row(modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.ic_back_arrow),
            contentDescription = null,
            modifier = Modifier
                .size(35.dp)
                .clip(CircleShape)
                .background(colorResource(id = R.color.metallic_yellow))
                .padding(9.dp)
                .clickable { onBackClicked() }
        )
    }
}

@Composable
fun HomeBottomNavigation(
    items: List<BottomNavItem>,
    currentRoute: String?,
    modifier: Modifier = Modifier,
    onBottomNavigationItemClicked: (route: String) -> Unit,
) {
    BottomNavigation(
        modifier = modifier.fillMaxWidth(), backgroundColor = Color.White, elevation = 0.dp
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                alwaysShowLabel = false,
                icon = {
                    Image(
                        painter = if (currentRoute == item.route) painterResource(id = item.selectedIcon) else painterResource(
                            id = item.unselectedIcon,
                        ),
                        contentDescription = null,
                    )
                },
                onClick = {
                    onBottomNavigationItemClicked(item.route)
                },
            )
        }
    }
}
