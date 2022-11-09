package com.onefinance.customerapp.presentation.transactions.transaction_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.onefinance.customerapp.core.dummy.FakeRecentlyTransaction
import com.onefinance.customerapp.core.presentation.composables.BaseTabLayout
import com.onefinance.customerapp.core.presentation.composables.tabs
import com.onefinance.customerapp.presentation.dashboard.BackTopBar
import com.onefinance.customerapp.presentation.dashboard.home.RecentlyTransactionItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TransactionListScreen(
    transactions: List<FakeRecentlyTransaction>,
    onBackClicked: () -> Unit,
    onTransactionClicked: (id: Int) -> Unit,
) {
    val pagerState = rememberPagerState()
    val tabIndex = pagerState.currentPage
    val scope = rememberCoroutineScope()

    Column {
        Spacer(modifier = Modifier.height(21.dp))
        BackTopBar(
            modifier = Modifier.padding(horizontal = 16.dp), onBackClicked = onBackClicked
        )
        Spacer(modifier = Modifier.height(37.dp))
        BaseTabLayout(modifier = Modifier.padding(horizontal = 16.dp),
            tabs = tabs,
            tabIndex = tabIndex,
            onTabSelected = { _, index ->
                scope.launch {
                    pagerState.animateScrollToPage(index)
                }

            })
        HorizontalPager(
            count = tabs.size, state = pagerState, modifier = Modifier.weight(1f)
        ) { page ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
            ) {
                items(transactions) { transaction ->
                    RecentlyTransactionItem(
                        transaction = transaction, onItemClicked = onTransactionClicked
                    )
                }
            }
        }
    }


}
