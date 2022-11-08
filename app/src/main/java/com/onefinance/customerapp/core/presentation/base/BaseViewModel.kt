package com.onefinance.customerapp.core.presentation.base

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onefinance.customerapp.R
import com.onefinance.customerapp.core.presentation.utils.UiText
import com.onefinance.customerapp.core.presentation.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {

    private var _uiState = MutableSharedFlow<ViewState>()
    val uiState = _uiState.asSharedFlow()

    protected val _uiAction = MutableSharedFlow<UiAction>()
    val uiAction = _uiAction.asSharedFlow()

    var isComposeLoading by mutableStateOf(false)

    private var unAuthorize = MutableSharedFlow<Boolean>()
    val immutableUnAuthorize = unAuthorize.asSharedFlow()

    private val _errorStateMessage = MutableSharedFlow<Int>()
    val errorStateMessage = _errorStateMessage.asSharedFlow()
    private val _hasError = Channel<Boolean>()
    val hasError = _hasError.receiveAsFlow()

    val _isRefreshing = MutableSharedFlow<Boolean>()
    val isRefreshing = _isRefreshing.asSharedFlow()


    /*
    * Basic Exception handler is used for any api doesn't have any extra customization exception
    * but if you have extra customization you will override this one and use your own condition
    * on the exceptions and add to them the standard exception by call CheckException() function
    * */
    open val basicExceptionHandler = BaseCoroutineExceptionHandler { _, _, throwable ->
        viewModelScope.launch {
            _hasError.trySend(true)
            isComposeLoading = false
            _isRefreshing.emit(false)
            _uiState.emit(ViewState.Loading(false))
            emitError(checkException(exception = throwable))
        }
    }

    suspend fun setLoading(compose: Boolean = false) {
        if (compose) isComposeLoading = true else _uiState.emit(ViewState.Loading(true))
    }

    suspend fun hideLoading(compose: Boolean = false) {
        if (compose) isComposeLoading = false else _uiState.emit(ViewState.Loading(false))
    }

    suspend fun emitError(@StringRes int: Int) {
        _uiState.emit(ViewState.Loading(false))
        _uiState.emit(ViewState.ShowError(UiText.StringResource(int)))
    }

    suspend fun emitError(uiText: UiText) {
        _uiState.emit(ViewState.Loading(false))
        if (uiText is UiText.StringResource && uiText.id == R.string.client_unauthorized_error) {
/*            localLogoutUseCase()*/
            unAuthorize.emit(true)
        } else {
            _uiState.emit(ViewState.ShowError(uiText))
        }
    }

/*    fun shouldShowErrorStateMessage(loadState: CombinedLoadStates) {
        viewModelScope.launch {
            if (loadState.refresh is LoadState.Error) {
                val error = (loadState.refresh as LoadState.Error).error
                _hasError.send(true)
                when(error){
                    is IOException -> emitError(R.string.internet_connection_error)
                    is BackendExceptions -> emitError(error.errorRes)
                    else -> emitError(R.string.backend_error_0)
                }

            }
        }
    }*/
}