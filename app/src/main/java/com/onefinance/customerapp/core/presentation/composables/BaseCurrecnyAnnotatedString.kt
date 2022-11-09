package com.onefinance.customerapp.core.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.onefinance.customerapp.R
import com.onefinance.customerapp.core.presentation.extensions.toCurrency
import com.onefinance.customerapp.ui.theme.poppins
import java.util.Locale

@Composable
fun currencyText(
    amount: Double,
    color: Color,
    currencyFontSize: TextUnit = 16.sp,
    amountFontSize: TextUnit = 16.sp,
    prefix: String? = null,
    prefixFontSize: TextUnit = 16.sp,
): AnnotatedString {
    return buildAnnotatedString {
        val locale = Locale.getDefault()
        if (!prefix.isNullOrEmpty())
            withStyle(
                SpanStyle(
                    color = color,
                    fontSize = prefixFontSize,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal
                )
            ) {
                append(prefix)
            }
        if (locale.language == Locale.ENGLISH.language)
            withStyle(
                SpanStyle(
                    color = color,
                    fontSize = currencyFontSize,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                )
            ) {
                append("${stringResource(id = R.string.common_egp)} ")
            }
//        append(" ")
        withStyle(
            SpanStyle(
                color = color,
                fontSize = amountFontSize,
                fontWeight = FontWeight.Bold,
                fontFamily = poppins
            )
        ) {
            append(amount.toCurrency())
        }

        if (locale == Locale("ar"))
            withStyle(
                SpanStyle(
                    color = color,
                    fontSize = currencyFontSize,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                )
            ) {
                append(" ${stringResource(id = R.string.common_egp)}")
            }

    }
}