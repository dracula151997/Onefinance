package com.onefinance.customerapp.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.onefinance.customerapp.BuildConfig
import com.onefinance.customerapp.R
import com.onefinance.customerapp.core.presentation.base.UiAction
import com.onefinance.customerapp.presentation.main.MainViewModel
import com.onefinance.customerapp.ui.theme.poppins
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
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
            viewModel.uiAction.collectLatest {
                when (it) {
                    is UiAction.NavigateWithDirection -> onNavigateToNextScreen(it.navDirections)
                    else -> {}
                }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = Color(0xffCDDAF6),
        topBar = {
            HomeTopAppBar(
                onNavigationDrawerClicked = {
                    scope.launch { scaffoldState.drawerState.open() }
                },
                onNotificationClicked = {},
                onProfileClicked = {},
            )
        },
        bottomBar = {
            HomeBottomNavigation(
                currentRoute = currentRoute,
                onBottomNavigationItemClicked = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                })
        },
        drawerContent = {
            DrawerContent(prepareNavigationDrawerItems()) { route ->
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
            NavHost(
                navController = navController,
                startDestination = BottomNavItem.Home.route,
                Modifier.padding(contentPadding)
            ) {
                composable(BottomNavItem.Home.route) {
                    Text(text = "Home")
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
            }
        },
    )
}

@Composable
fun HomeTopAppBar(
    onNotificationClicked: () -> Unit,
    onProfileClicked: () -> Unit,
    onNavigationDrawerClicked: () -> Unit,
) {
    TopAppBar(
        backgroundColor = Color(0xffF6D89C),
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
fun DashboardScreen() {

}

@Composable
fun DrawerContent(
    items: List<DrawerNavScreen>,
    onItemClicked: (route: String) -> Unit,
) {
    Column {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 25.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            item {
                DrawerHeader()
            }
            items(items) { item ->
                DrawerNavigationItem(item, onClick = { onItemClicked(item.route) })
            }
        }

        DrawerFooter()
    }
}

@Composable
fun DrawerFooter(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 27.dp, start = 17.dp, end = 17.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = stringResource(id = R.string.drawer_nan_footer_title, BuildConfig.VERSION_NAME),
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = poppins,
            color = colorResource(id = R.color.footer_text_color)
        )
    }
}

@Composable
fun DrawerNavigationItem(item: DrawerNavScreen, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick, interactionSource = remember {
                MutableInteractionSource()
            }, indication = null), verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = item.icon),
            contentDescription = null,
            modifier = Modifier
                .size(25.dp)
                .padding(4.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(id = item.label),
            fontFamily = poppins,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
    }
}

@Composable
fun DrawerHeader() {
    Spacer(modifier = Modifier.height(66.dp))
    Image(
        painter = painterResource(id = R.drawable.ic_one_finance_drawer_icon),
        contentDescription = null,
    )
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = "Hello \uD83D\uDC4B",
        fontFamily = poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "Ahmed Ibrahim",
        fontFamily = poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    )
    Spacer(modifier = Modifier.height(13.dp))
    Divider(
        color = colorResource(id = R.color.quick_silver),
        thickness = 1.dp,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(13.dp))
}

@Composable
fun HomeBottomNavigation(
    currentRoute: String?,
    modifier: Modifier = Modifier,
    onBottomNavigationItemClicked: (route: String) -> Unit,
) {
    val items = listOf(
        BottomNavItem.Home, BottomNavItem.Card, BottomNavItem.Offers, BottomNavItem.Calculator
    )
    BottomNavigation(modifier = modifier.fillMaxWidth(), backgroundColor = Color.White) {
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

fun prepareNavigationDrawerItems(): List<DrawerNavScreen> {
    return listOf(
        DrawerNavScreen.Installments,
        DrawerNavScreen.TransactionHistory,
        DrawerNavScreen.OurBranches,
        DrawerNavScreen.ContactUs,
        DrawerNavScreen.FAQs,
        DrawerNavScreen.TermsAndCondition,
        DrawerNavScreen.Logout
    )
}