package com.onefinance.customerapp.core.presentation.base

import androidx.fragment.app.Fragment
import com.onefinance.customerapp.core.presentation.utils.UiText
import com.onefinance.customerapp.core.presentation.views.LoadingDialog
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {
    @Inject
    lateinit var mProgressViewDialog: LoadingDialog


    internal fun showErrorDialog(uiText: UiText) {
        ErrorDialog(
            uiText.asString(requireContext())
        ).show(childFragmentManager, null)
    }

    internal fun showLoading(message: String) {
        mProgressViewDialog.show(message)
    }

    internal fun hideLoading() {
        mProgressViewDialog.stop()
    }
}