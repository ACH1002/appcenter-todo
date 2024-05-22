package com.example.appcenter_todolist.model

import com.google.gson.annotations.SerializedName

data class CommonResponse<T> (
    @SerializedName("message")
    val message : String,
    @SerializedName("response")
    val response : T
)