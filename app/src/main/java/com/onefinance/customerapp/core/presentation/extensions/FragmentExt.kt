package com.onefinance.customerapp.core.presentation.extensions

import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.onefinance.customerapp.core.presentation.base.BaseFragment
import com.onefinance.customerapp.core.presentation.base.BaseViewModel
import com.onefinance.customerapp.core.presentation.utils.ViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun <V : BaseViewModel> BaseFragment<V>.bindViewModel(viewModel: V) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                viewModel.uiState.collect { event ->
                    when (event) {
                        is ViewState.Loading -> {
                            if (event.show)
                                showLoading(event.message.asString(requireContext()))
                            else
                                hideLoading()
                        }
                        is ViewState.ShowError -> {
                            showErrorDialog(event.uiText)
                        }
                    }
                }
            }

            launch {
                viewModel.immutableUnAuthorize.collect {
                    if (it) {
//                    navigateToLogin()
                    }
                }
            }


        }
    }
}

fun <V : BaseViewModel> BaseFragment<V>.hideKeyboard() {
    val imm =
        requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
}

fun <V : BaseViewModel> BaseFragment<V>.showToast(@StringRes message: Int) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

}

fun <V : BaseViewModel> BaseFragment<V>.focusEditText(
    view: View,
) {
    view.requestFocus()
    val imm =
        requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm!!.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
}

fun <V : BaseViewModel> BaseFragment<V>.collectInLifeCycle(
    state: Lifecycle.State = Lifecycle.State.STARTED,
    block: CoroutineScope.() -> Unit,
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(state) {
            block(this)
        }
    }
}