package com.onefinance.customerapp.presentation.dashboard.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.onefinance.customerapp.R
import com.onefinance.customerapp.core.dummy.FakeRecentlyTransaction
import com.onefinance.customerapp.core.dummy.TransactionStatus
import com.onefinance.customerapp.core.dummy.dummyRecentlyTransactions
import com.onefinance.customerapp.core.presentation.composables.BaseTabLayout
import com.onefinance.customerapp.core.presentation.composables.HomeTab
import com.onefinance.customerapp.core.presentation.composables.LimitColumn
import com.onefinance.customerapp.core.presentation.composables.currencyText
import com.onefinance.customerapp.core.presentation.composables.tabs
import com.onefinance.customerapp.ui.theme.poppins
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    onSeeAllClicked: () -> Unit,
    onRecentlyTransactionClicked: (id: Int) -> Unit,
) {
    val pagerState = rememberPagerState()
    val tabIndex = pagerState.currentPage
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .scrollable(
                state = rememberScrollState(), orientation = Orientation.Vertical
            )
    ) {
        LimitSection(pagerState = pagerState, tabIndex = tabIndex, tabs = tabs) { _, index ->
            scope.launch {
                pagerState.animateScrollToPage(index)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        RecentlyTransactionsSection(
            onRecentlyTransactionClicked = onRecentlyTransactionClicked,
            onSeeAllClicked = onSeeAllClicked
        )

    }
}

@Composable
private fun RecentlyTransactionsSection(
    onSeeAllClicked: () -> Unit,
    onRecentlyTransactionClicked: (id: Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.home_recently_transaction_header),
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Bold,
            fontFamily = poppins,
            fontSize = 15.sp,
            color = colorResource(id = R.color.chinese_black)
        )
        Text(
            text = stringResource(id = R.string.home_see_all_header),
            fontWeight = FontWeight.SemiBold,
            fontFamily = poppins,
            fontSize = 13.sp,
            color = colorResource(id = R.color.light_blue),
            modifier = Modifier.clickable(
                onClick = onSeeAllClicked,
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null,
            )
        )
    }

    RecentlyTransactionsList(onRecentlyTransactionClicked)
}

@Composable
private fun RecentlyTransactionsList(
    onRecentlyTransactionClicked: (id: Int) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 3.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(dummyRecentlyTransactions) { transaction ->
            RecentlyTransactionItem(transaction, onRecentlyTransactionClicked)
        }
    }
}

@Composable
fun RecentlyTransactionItem(
    transaction: FakeRecentlyTransaction,
    onItemClicked: (id: Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClicked(transaction.id) },
        shape = RoundedCornerShape(9.dp),
        elevation = 0.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 17.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_transaction_img),
                contentDescription = null,
                modifier = Modifier
                    .size(45.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(colorResource(id = R.color.blanched_almond))
                    .padding(10.dp),
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.width(6.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = transaction.merchantName,
                    fontSize = 13.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(id = R.color.charleston_green)
                )
                Text(
                    text = transaction.date,
                    fontWeight = FontWeight.Normal,
                    fontFamily = poppins,
                    fontSize = 9.sp,
                    color = colorResource(id = R.color.spanish_gray)
                )
            }
            Column {
                Text(
                    text = currencyText(
                        amount = transaction.amount,
                        color = colorResource(id = R.color.charleston_green)
                    )
                )
                TransactionStatus(status = transaction.status)

            }
        }
    }
}

@Composable
@OptIn(ExperimentalPagerApi::class)
private fun LimitSection(
    pagerState: PagerState,
    tabIndex: Int,
    tabs: List<HomeTab>,
    onTabSelected: (tab: HomeTab, index: Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(colorResource(id = R.color.drawer_layout_color))
    ) {
        BaseTabLayout(
            onTabSelected = onTabSelected,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 60.dp, end = 60.dp, top = 7.dp),
            tabIndex = tabIndex,
            tabs = tabs
        )
        HorizontalPager(count = tabs.size, state = pagerState) {
            LimitColumn(
                modifier = Modifier.padding(vertical = 16.dp), limit = currencyText(
                    amount = 5000000.0, color = colorResource(id = R.color.charleston_green)
                ), spent = currencyText(
                    amount = 2000000.0, color = colorResource(id = R.color.charleston_green)
                )
            )
        }
    }
}

@Composable
fun TransactionStatus(
    status: TransactionStatus,
) {
    Row(
        horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = status.iconRes),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = stringResource(id = status.statusNameRes),
            fontSize = 11.sp,
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
            color = colorResource(id = R.color.transaction_status_color)

        )
    }
}

