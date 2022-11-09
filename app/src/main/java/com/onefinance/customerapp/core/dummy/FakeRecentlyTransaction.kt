package com.onefinance.customerapp.core.dummy

data class FakeRecentlyTransaction(
    val id: Int,
    val merchantName: String,
    val amount: Double,
    val date: String,
    val status: TransactionStatus,
)

sealed class TransactionStatus {
    object Pending : TransactionStatus()
    object Completed : TransactionStatus()
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