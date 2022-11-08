package com.onefinance.customerapp.presentation.dashboard.home

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.onefinance.customerapp.R
import com.onefinance.customerapp.core.presentation.composables.BaseTabLayout
import com.onefinance.customerapp.core.presentation.composables.HomeTab
import com.onefinance.customerapp.core.presentation.composables.LimitColumn
import com.onefinance.customerapp.core.presentation.extensions.toCurrency
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen() {
    val pagerState = rememberPagerState()
    val tabIndex = pagerState.currentPage
    val scope = rememberCoroutineScope()
    val tabs = listOf(
        HomeTab.Possibilities,
        HomeTab.Card,
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .scrollable(
                state = rememberScrollState(),
                orientation = Orientation.Vertical
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(colorResource(id = R.color.drawer_layout_color))
        ) {
            BaseTabLayout(
                onTabSelected = { tab, index ->
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 60.dp, end = 60.dp, top = 7.dp),
                tabIndex = tabIndex,
                tabs = tabs
            )
            HorizontalPager(count = tabs.size, state = pagerState) {
                LimitColumn(
                    modifier = Modifier.padding(vertical = 16.dp),
                    limit = 3000.0.toCurrency(),
                    spent = 1000.0.toCurrency()
                )
            }
        }
    }
}

