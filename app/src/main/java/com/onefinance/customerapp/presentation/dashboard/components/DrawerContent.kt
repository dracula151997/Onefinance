package com.onefinance.customerapp.presentation.dashboard.components

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
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.onefinance.customerapp.BuildConfig
import com.onefinance.customerapp.R
import com.onefinance.customerapp.ui.theme.poppins

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
