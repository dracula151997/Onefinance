package com.onefinance.customerapp.core.presentation.utils

sealed class ViewState {
    data class ShowError(val uiText: UiText) : ViewState()
    data class Loading(val show: Boolean, val message: UiText = UiText.emptyStringResource()) :
        ViewState()
}