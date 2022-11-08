package com.onefinance.customerapp.core.presentation.extensions

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

fun Double.toCurrency(): String {
    val nf = NumberFormat.getNumberInstance(Locale.US)
    val formatter = nf as DecimalFormat
    formatter.applyPattern("###,###,###.##")
    return formatter.format(this)
}