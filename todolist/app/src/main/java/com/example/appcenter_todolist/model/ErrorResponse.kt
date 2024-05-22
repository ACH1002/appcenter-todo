package com.example.appcenter_todolist.model

import com.google.gson.annotations.SerializedName
//에러 응답 DTO
data class ErrorResponse(
    @SerializedName("message")
    val message : String,
    @SerializedName("validationErrors")
    val validationErrors : List<ValidationError>
)
