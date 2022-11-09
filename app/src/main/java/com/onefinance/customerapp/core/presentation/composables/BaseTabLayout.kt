package com.onefinance.customerapp.core.presentation.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.onefinance.customerapp.R
import com.onefinance.customerapp.ui.theme.poppins

sealed class HomeTab(@StringRes val stringRes: Int) {
    object Possibilities : HomeTab(R.string.home_possibilities_tab_title)
    object Card : HomeTab(R.string.home_card_tab_title)
}

@Composable
fun BaseTabLayout(
    tabs: List<HomeTab>,
    tabIndex: Int,
    onTabSelected: (tab: HomeTab, index: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    TabRow(modifier = modifier
        .fillMaxWidth()
        .heightIn(60.dp)
        .clip(RoundedCornerShape(30.dp)),
        selectedTabIndex = tabIndex,
        backgroundColor = colorResource(id = R.color.caramel_2),
        indicator = {
            TabRowDefaults.Indicator(
                height = 0.dp, color = Color.Transparent
            )
        }) {
        tabs.forEachIndexed { index, tab ->
            BaseTab(
                tab = tab,
                selectedTab = tabIndex == index
            ) { onTabSelected(tab, index) }
        }
    }
}

@Composable
private fun BaseTab(
    tab: HomeTab,
    selectedTab: Boolean,
    onClick: () -> Unit,
) {
    Tab(
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 5.dp)
            .clip(RoundedCornerShape(29.dp))
            .background(if (!selectedTab) Color.Transparent else colorResource(id = R.color.marigold)),
        selected = selectedTab,
        unselectedContentColor = colorResource(id = R.color.marigold),
        selectedContentColor = colorResource(id = R.color.raisin_black),
        onClick = onClick
    ) {
        Text(
            modifier = textModifier,
            text = stringResource(id = tab.stringRes),
            color = if (!selectedTab) Color(0xffEBA921) else colorResource(id = R.color.raisin_black),
            fontFamily = poppins,
            fontWeight = FontWeight.Bold
        )
    }
}

private val textModifier = Modifier.padding(vertical = 6.dp, horizontal = 4.dp)

@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun HomeTabLayout() {
    val tabs = listOf(
        HomeTab.Card,

        )

}

@Composable
fun LimitCircle(
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .sizeIn(minWidth = 216.dp, minHeight = 216.dp)
            .border(
                width = 1.dp, brush = Brush.linearGradient(
                    listOf(
                        Color(0xffEBA921),
                        Color(0xffFCF0D9),
                        Color(0xffEBA921),
                    ), tileMode = TileMode.Mirror
                ), shape = CircleShape
            ), contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun LimitColumn(
    limit: AnnotatedString,
    spent: AnnotatedString,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.background(Color(0xffFCF0D9))
    ) {
        LimitCircle {
            Column(
                modifier = Modifier
                    .widthIn(177.dp)
                    .heightIn(177.dp)
                    .border(
                        width = 5.dp,
                        color = Color(0xffF6D89C),
                        shape = CircleShape,
                    )
                    .clip(CircleShape)
                    .background(colorResource(id = R.color.marigold)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.home_limit_title),
                    fontFamily = poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.caramel_2)
                )
                Text(
                    text = limit,
                    fontFamily = poppins,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(
                        id = R.color.charleston_green
                    ),
                )
                Text(
                    text = stringResource(id = R.string.home_spent_title),
                    fontFamily = poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.caramel_2)
                )
                Text(
                    text = spent,
                    fontFamily = poppins,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(
                        id = R.color.charleston_green
                    )
                )
            }
        }

    }
}