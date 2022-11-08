package com.onefinance.customerapp.core.data.exceptions

sealed class BackendError constructor(val code: String) {

    object ExceptionError : BackendError("0")
    object FailedInGetData : BackendError("1")
    object NotFound : BackendError("2")
    object PhoneNotMatch : BackendError("3")
    object InvalidOTPCode : BackendError("4")
    object InvalidPhone : BackendError("5")
    object InvalidPassword : BackendError("6")
    object FailedSaveData : BackendError("7")
    object AccountNotFound : BackendError("8")
    object ErrorSendOTP : BackendError("9")
    object AccountFound : BackendError("10")
    object PhoneFound : BackendError("11")
    object AgeOutOfRange : BackendError("12")
    object NationalIDNotFound : BackendError("13")
    object OTPTimes : BackendError("14")
    object AccountLocked : BackendError("15")
    object InvalidToken : BackendError("16")
    object GuestToken : BackendError("17")
    object UserNameIsExist : BackendError("18")
    object ClientIDNotMatch : BackendError("19")
    object CouldNotRetrieveInstallment : BackendError("20")
    object FailedInTransactionCanceled : BackendError("21")
    object LoanAmountMoreThanAvailable : BackendError("22")
    object LimitNotActive : BackendError("23")
    object InvalidUDID : BackendError("24")
    object TransactionExpired : BackendError("25")
    object LoanAmountLeesThanMinimum : BackendError("26")
    object TransactionNotPending : BackendError("27")
    object TransactionNotBooked : BackendError("28")
    object StatusNavigationAlreadyExist : BackendError("29")
    object InvalidQRCode : BackendError("30")
    object InvalidPhoneOrNationalId : BackendError("31")
    object PendingTransaction : BackendError("33")
    object TransactionAmountLessThanMinAmount : BackendError("34")
    object InvalidGlobalID : BackendError("36")
}


fun getAccordingException(code: String): Throwable {
    return when (code) {
        BackendError.ExceptionError.code -> BackendExceptions.UnknownException
        BackendError.FailedInGetData.code -> BackendExceptions.GetDataFailedException
        BackendError.NotFound.code -> BackendExceptions.NotFoundException
        BackendError.PhoneNotMatch.code -> BackendExceptions.PhoneNotMatchException
        BackendError.InvalidOTPCode.code -> BackendExceptions.InvalidOTPCodeException
        BackendError.InvalidPhone.code -> BackendExceptions.InvalidPhone
        BackendError.InvalidPassword.code -> BackendExceptions.InvalidPassword
        BackendError.FailedSaveData.code -> BackendExceptions.SaveDataFailedException
        BackendError.AccountNotFound.code -> BackendExceptions.AccountNotFoundException
        BackendError.ErrorSendOTP.code -> BackendExceptions.SendOtpErrorException
        BackendError.AccountFound.code -> BackendExceptions.AccountFoundException
        BackendError.PhoneFound.code -> BackendExceptions.PhoneFoundException
        BackendError.AgeOutOfRange.code -> BackendExceptions.AgeOutOfRangeException
        BackendError.NationalIDNotFound.code -> BackendExceptions.NationIdNotFoundException
        BackendError.OTPTimes.code -> BackendExceptions.OtpTimesException
        BackendError.AccountLocked.code -> BackendExceptions.AccountLockedException
        BackendError.InvalidToken.code -> BackendExceptions.InvalidTokenException
        BackendError.GuestToken.code -> BackendExceptions.GuestTokenException
        BackendError.UserNameIsExist.code -> BackendExceptions.UsernameExistException
        BackendError.ClientIDNotMatch.code -> BackendExceptions.ClientIdNotMatchException
        BackendError.CouldNotRetrieveInstallment.code -> BackendExceptions.CouldRetrieveInstallmentException
        BackendError.FailedInTransactionCanceled.code -> BackendExceptions.CancelTransactionFailedException
        BackendError.LoanAmountMoreThanAvailable.code -> BackendExceptions.LoanAmountMoreThanAvailableException
        BackendError.LimitNotActive.code -> BackendExceptions.LimitNotActiveException
        BackendError.InvalidUDID.code -> BackendExceptions.InvalidUDIDException
        BackendError.TransactionExpired.code -> BackendExceptions.TransactionExpiredException
        BackendError.LoanAmountLeesThanMinimum.code -> BackendExceptions.LoanAmountLessThanMinimumException
        BackendError.TransactionNotPending.code -> BackendExceptions.TransactionNotPendingException
        BackendError.TransactionNotBooked.code -> BackendExceptions.TransactionNotBookedException
        BackendError.StatusNavigationAlreadyExist.code -> BackendExceptions.StatusNavigationAlreadyExistException
        BackendError.InvalidQRCode.code -> BackendExceptions.InvalidQRCodeException
        BackendError.InvalidPhoneOrNationalId.code -> BackendExceptions.InvalidPhoneOrNationalIdException
        BackendError.StatusNavigationAlreadyExist.code -> BackendExceptions.StatusNavigationAlreadyExistException
        BackendError.PendingTransaction.code -> BackendExceptions.PendingTransactionException
        BackendError.TransactionAmountLessThanMinAmount.code -> BackendExceptions.TransactionAmountLessThanMinAmountException
        BackendError.InvalidGlobalID.code -> BackendExceptions.InvalidGlobalID
        else -> {
            BackendExceptions.UnknownException
        }
    }
}
