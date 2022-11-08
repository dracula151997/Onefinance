package com.onefinance.customerapp.core.data.utils

import com.google.gson.annotations.SerializedName

class BasicApiResponse<T> {
    @SerializedName("data")
    val data: T? = null

    @SerializedName("errors")
    val error: ResponseException? = null

    @SerializedName("isSuccess")
    val isSuccess: Boolean = false
}