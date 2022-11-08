package com.onefinance.customerapp.presentation.onboarding

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.onefinance.customerapp.R
import com.onefinance.customerapp.core.presentation.base.UiAction
import com.onefinance.customerapp.core.presentation.composables.PrimaryButton
import com.onefinance.customerapp.core.presentation.composables.PrimaryOutlinedButton
import com.onefinance.customerapp.ui.theme.poppins
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    viewModel: OnBoardingViewModel = hiltViewModel(),
    pages: List<OnBoardingPage> = viewModel.getOnBoardingData(),
    count: Int = viewModel.getOnBoardingData().size,
    navigateToNextScreen: (NavDirections) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = Unit) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                viewModel.uiAction.collect {
                    when (it) {
                        is UiAction.NavigateWithDirection -> navigateToNextScreen(it.navDirections)
                        else -> {}
                    }
                }
            }

        }

    }
    Surface(
        color = colorResource(id = R.color.metallic_yellow),
        modifier = modifier.fillMaxSize()
    ) {
        val pagerState = rememberPagerState()
        val scope = rememberCoroutineScope()

        Column(modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .verticalScroll(rememberScrollState())) {
            Spacer(modifier = Modifier.height(16.dp))
            OnBoardingHeader(onClick = { viewModel.skipOnBoarding() },
                modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(modifier = Modifier.height(50.dp))
            HorizontalPager(count = count,
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)) { page ->
                OnBoardingPage(page = pages[page], modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight())

            }
            Spacer(modifier = Modifier.height(30.dp))
            PagerIndicators(
                size = pages.size,
                index = pagerState.currentPage,
                spaceBetweenIndicators = 3.dp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(30.dp))
            OnBoardingFooter(
                pagerState.currentPage,
                onFirstLoginClicked = { viewModel.navigateToFirstLoginScreen() },
                onNextClicked = { scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) } },
                onSignInClicked = { viewModel.navigateToLoginScreen() },
            )
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun OnBoardingFooter(
    currentPage: Int,
    onFirstLoginClicked: () -> Unit,
    onSignInClicked: () -> Unit,
    onNextClicked: () -> Unit,

    ) {
    if (currentPage == 2) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            PrimaryOutlinedButton(
                text = stringResource(id = R.string.onboarding_first_signin_btn),
                modifier = Modifier
                    .weight(1f)
                    .heightIn(64.dp),
                buttonColors = ButtonDefaults.buttonColors(
                    contentColor = colorResource(id = R.color.charleston_green),
                    backgroundColor = Color.Transparent,
                ),
                borderStroke = BorderStroke(
                    1.dp,
                    color = colorResource(id = R.color.charleston_green),
                ),
                onClick = onFirstLoginClicked,
            )

            Spacer(modifier = Modifier.width(40.dp))
            PrimaryButton(
                text = stringResource(id = R.string.onboarding_signin_btn),
                modifier = Modifier
                    .weight(1f)
                    .heightIn(64.dp),
                buttonColors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.charleston_green),
                    contentColor = colorResource(id = R.color.metallic_yellow)
                ),
                onClick = onSignInClicked

            )
        }

    } else {
        PrimaryOutlinedButton(
            stringResource(id = R.string.onboarding_next_btn),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(64.dp)
                .padding(horizontal = 83.dp),
            borderStroke = BorderStroke(1.dp,
                color = colorResource(id = R.color.charleston_green)),
            buttonColors = ButtonDefaults.buttonColors(
                contentColor = colorResource(id = R.color.charleston_green),
                backgroundColor = Color.Transparent
            ),
            onClick = onNextClicked
        )

    }
}


@Composable
fun OnBoardingPage(
    page: OnBoardingPage,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(340.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            listOf(
                                colorResource(id = R.color.metallic_yellow_2),
                                colorResource(id = R.color.white),
                            ),
                            tileMode = TileMode.Mirror
                        ),
                    ),
                contentAlignment = Alignment.Center
            ) {
                OnBoardingImage(page)
            }
            Spacer(modifier = Modifier.height(20.dp))
            OnBoardingTitle(page)
            Spacer(modifier = Modifier.height(8.dp))
            OnBoardingDescription(page)
            Spacer(modifier = Modifier.height(8.dp))
            OnBoardingSubDescription(page)

        }
    }
}

@Composable
private fun OnBoardingSubDescription(page: OnBoardingPage) {
    Text(
        text = stringResource(id = page.subDescription),
        textAlign = TextAlign.Center,
        fontSize = 12.sp,
        fontFamily = poppins,
        fontWeight = FontWeight.Medium,
        color = colorResource(id = R.color.charleston_green),
        modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = 50.dp)
    )
}

@Composable
private fun OnBoardingDescription(page: OnBoardingPage) {
    Text(
        text = stringResource(id = page.description),
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        color = colorResource(id = R.color.chinese_black),
        fontFamily = poppins,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
private fun OnBoardingTitle(page: OnBoardingPage) {
    Text(
        text = stringResource(id = page.title),
        fontSize = 20.sp,
        fontFamily = poppins,
        fontWeight = FontWeight.SemiBold,
        color = colorResource(id = R.color.charleston_green).copy(alpha = 0.4f),
    )
}

@Composable
private fun OnBoardingImage(page: OnBoardingPage) {
    Image(
        painter = painterResource(id = page.image), contentDescription = null,
        alignment = Alignment.Center,
        modifier = Modifier
            .size(286.dp)
    )
}

@Composable
fun OnBoardingHeader(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_one_finance_black),
            contentDescription = null,
            modifier = Modifier.weight(1f),
            alignment = Alignment.TopStart
        )
        TextButton(onClick = onClick) {
            Text(
                text = stringResource(id = R.string.onboarding_skip_btn_title),
                color = colorResource(
                    id = R.color.chinese_black,
                ),
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
            )
        }


    }
}

@Composable
fun PagerIndicators(
    size: Int,
    index: Int,
    modifier: Modifier = Modifier,
    spaceBetweenIndicators: Dp = 12.dp,
) {
    Box(
        modifier = modifier
    ) {
        Indicators(size, index, modifier, spaceBetweenIndicators)
    }
}

@Composable
fun Indicators(
    size: Int,
    index: Int,
    modifier: Modifier = Modifier,
    spaceBetweenIndicators: Dp = 12.dp,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = spaceBetweenIndicators),
        modifier = modifier
    ) {
        repeat(size) {
            Indicator(isSelected = it == index)
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean) {
    val width = animateDpAsState(
        targetValue = if (isSelected) 25.dp else 10.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
    )

    Box(
        modifier = Modifier
            .height(8.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                color = if (isSelected) colorResource(id = R.color.active_indicator_color) else colorResource(
                    id = R.color.inactive_indicator_color)
            )
    )
}
