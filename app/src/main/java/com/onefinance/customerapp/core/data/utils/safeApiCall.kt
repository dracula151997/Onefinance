package com.onefinance.customerapp.core.data.utils

import com.onefinance.customerapp.core.data.exceptions.HttpExceptionCode
import com.onefinance.customerapp.core.data.exceptions.StandardErrors
import com.onefinance.customerapp.core.data.exceptions.getAccordingException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

inline fun <T> safeApiCall(
    crossinline action: suspend () -> BasicApiResponse<T>,
): Flow<ApiResult<T>> = flow {
    try {
        val response = action()
        if (response.isSuccess) emit(ApiResult.Success(response.data))
        else throw getAccordingException(response.error?.code.orEmpty())
    } catch (e: IOException) {
        throw StandardErrors.InternetError
    } catch (e: HttpException) {
        if (e.code() == HttpExceptionCode.Unauthorized.code) throw StandardErrors.UnAuthorize
        else throw StandardErrors.UnknownError
    } catch (e: Exception) {
        throw StandardErrors.UnknownError
    }
}