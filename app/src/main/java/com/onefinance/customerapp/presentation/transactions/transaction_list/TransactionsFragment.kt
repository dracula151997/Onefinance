package com.onefinance.customerapp.presentation.transactions.transaction_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.navigation.findNavController
import com.onefinance.customerapp.core.dummy.dummeyTransactions
import com.onefinance.customerapp.core.presentation.base.BaseFragment
import com.onefinance.customerapp.core.presentation.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionsFragment : BaseFragment<BaseViewModel>() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            TransactionListScreen(transactions = dummeyTransactions,
                onBackClicked = { findNavController().navigateUp() }) {

            }
        }
    }
}