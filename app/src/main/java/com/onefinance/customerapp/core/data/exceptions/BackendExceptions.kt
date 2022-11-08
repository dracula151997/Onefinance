package com.onefinance.customerapp.core.data.exceptions

import androidx.annotation.StringRes
import com.onefinance.customerapp.R

sealed class BackendExceptions(@StringRes open val errorRes: Int) : Throwable() {
    object UnknownException : BackendExceptions(R.string.backend_error_0)
    object GetDataFailedException : BackendExceptions(R.string.backend_error_0)
    object NotFoundException : BackendExceptions(R.string.backend_error_0)
    object PhoneNotMatchException : BackendExceptions(R.string.backend_error_0)
    object InvalidOTPCodeException : BackendExceptions(R.string.backend_error_0)
    object InvalidPhone : BackendExceptions(R.string.backend_error_0)
    object InvalidPassword : BackendExceptions(R.string.backend_error_0)
    object SaveDataFailedException : BackendExceptions(R.string.backend_error_0)
    object AccountNotFoundException : BackendExceptions(R.string.backend_error_0)
    object SendOtpErrorException : BackendExceptions(R.string.backend_error_0)
    object AccountFoundException : BackendExceptions(R.string.backend_error_0)
    object PhoneFoundException : BackendExceptions(R.string.backend_error_0)
    object AgeOutOfRangeException : BackendExceptions(R.string.backend_error_0)
    object NationIdNotFoundException : BackendExceptions(R.string.backend_error_0)
    object OtpTimesException : BackendExceptions(R.string.backend_error_0)
    object AccountLockedException : BackendExceptions(R.string.backend_error_0)
    object InvalidTokenException : BackendExceptions(R.string.backend_error_0)
    object GuestTokenException : BackendExceptions(R.string.backend_error_0)
    object UsernameExistException : BackendExceptions(R.string.backend_error_0)
    object ClientIdNotMatchException : BackendExceptions(R.string.backend_error_0)
    object CouldRetrieveInstallmentException : BackendExceptions(R.string.backend_error_0)
    object CancelTransactionFailedException : BackendExceptions(R.string.backend_error_0)
    object LoanAmountMoreThanAvailableException : BackendExceptions(R.string.backend_error_0)
    object LimitNotActiveException : BackendExceptions(R.string.backend_error_0)
    object InvalidUDIDException : BackendExceptions(R.string.backend_error_0)
    object TransactionExpiredException : BackendExceptions(R.string.backend_error_0)
    object LoanAmountLessThanMinimumException : BackendExceptions(R.string.backend_error_0)
    object TransactionNotPendingException : BackendExceptions(R.string.backend_error_0)
    object TransactionNotBookedException : BackendExceptions(R.string.backend_error_0)
    object StatusNavigationAlreadyExistException : BackendExceptions(R.string.backend_error_0)
    object InvalidQRCodeException : BackendExceptions(R.string.backend_error_0)
    object InvalidPhoneOrNationalIdException : BackendExceptions(R.string.backend_error_0)
    object PendingTransactionException : BackendExceptions(R.string.backend_error_0)
    object TransactionAmountLessThanMinAmountException :
        BackendExceptions(R.string.backend_error_0)

    object InvalidGlobalID : BackendExceptions(R.string.backend_error_0)


}