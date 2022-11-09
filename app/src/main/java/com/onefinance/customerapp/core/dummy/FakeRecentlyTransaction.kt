package com.onefinance.customerapp.core.dummy

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.onefinance.customerapp.R

data class FakeRecentlyTransaction(
    val id: Int,
    val merchantName: String,
    val amount: Double,
    val date: String,
    val status: TransactionStatus,
)

sealed class TransactionStatus(
    @DrawableRes val iconRes: Int,
    @StringRes val statusNameRes: Int,
) {
    object Pending : TransactionStatus(
        R.drawable.ic_pending_status,
        R.string.transaction_status_pending
    )

    object Completed : TransactionStatus(
        R.drawable.ic_completed_status,
        R.string.transaction_status_completed
    )
}

val dummyRecentlyTransactions = listOf(
    FakeRecentlyTransaction(
        1,
        "Tradeline",
        30000.0,
        "1/1/2022",
        TransactionStatus.Pending
    ),
    FakeRecentlyTransaction(
        2,
        "Tradeline",
        20000.0,
        "1/1/2021",
        TransactionStatus.Completed
    )
)