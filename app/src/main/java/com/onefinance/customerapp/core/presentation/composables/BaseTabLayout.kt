package com.onefinance.customerapp.core.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.onefinance.customerapp.R
import com.onefinance.customerapp.ui.theme.poppins

enum class HomeTab(val title: String) {
    Possibilities("Possibilities"),
    Card("Card")
}

@Composable
fun OnefinanceTabs(
    onTabSelected: (HomeTab) -> Unit,
    selectedTab: HomeTab,
    modifier: Modifier = Modifier,
) {
    TabRow(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(60.dp)
            .clip(RoundedCornerShape(30.dp)),
        selectedTabIndex = selectedTab.ordinal,
        backgroundColor = Color(0xffFFDA8D),
        indicator = {
            TabRowDefaults.Indicator(
                height = 0.dp,
                color = Color.Transparent
            )
        }
    ) {
        HomeTab.values().forEachIndexed { index, tab ->
            val selected = index == selectedTab.ordinal
            SleepTabText(
                sleepTab = tab,
                selected = selected,
                onTabSelected = onTabSelected,
                index = index
            )


        }
    }
}

@Composable
private fun SleepTabText(
    sleepTab: HomeTab,
    selected: Boolean,
    index: Int,
    onTabSelected: (HomeTab) -> Unit,
) {
    Tab(
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 5.dp)
            .clip(RoundedCornerShape(29.dp))
            .background(if (!selected) Color.Transparent else colorResource(id = R.color.active_indicator_color)),
        selected = selected,
        unselectedContentColor = colorResource(id = R.color.caramel),
        selectedContentColor = colorResource(id = R.color.metallic_yellow),
        onClick = {
            onTabSelected(HomeTab.values()[index])
        }
    ) {
        Text(
            modifier = textModifier,
            text = sleepTab.title,
            color = if (!selected) Color(0xffEBA921) else colorResource(id = R.color.raisin_black),
            fontFamily = poppins,
            fontWeight = FontWeight.Bold
        )
    }
}

private val textModifier = Modifier
    .padding(vertical = 6.dp, horizontal = 4.dp)

@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun HomeTabLayout() {
    val selectedTab = remember {
        mutableStateOf(HomeTab.Possibilities)
    }
    OnefinanceTabs(onTabSelected = {
        selectedTab.value = it
    }, selectedTab = selectedTab.value)
}

@Composable
fun LimitCircle(
    content: @Composable () -> Unit,
) {
    Box(modifier = Modifier
        .clip(CircleShape)
        .sizeIn(minWidth = 216.dp, minHeight = 216.dp)
        .border(
            width = 1.dp,
            brush = Brush.linearGradient(
                listOf(
                    Color(0xffEBA921),
                    Color(0xffFCF0D9),
                    Color(0xffEBA921),
                ),
                tileMode = TileMode.Mirror
            ),
            shape = CircleShape),
        contentAlignment = Alignment.Center) {
        content()
    }
}

@Composable
@Preview(widthDp = 350, heightDp = 500)
fun LimitColumn() {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(Color(0xffFCF0D9))) {
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
                Text(text = "Limit")
                Text(text = "EGP5,000,000")
                Text(text = "Spent")
                Text(text = "EGP1,000,000")
            }
        }

    }
}