package com.example.appcenter_todolist.model.error

import com.google.gson.annotations.SerializedName

//유효성 검사 에러 발생 시 내용들
data class ValidationError(
    @SerializedName("field")
    val field : String,
    @SerializedName("value")
    val value : String,
    @SerializedName("reason")
    val reason : String,
)
