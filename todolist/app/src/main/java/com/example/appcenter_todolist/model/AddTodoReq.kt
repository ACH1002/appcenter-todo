package com.example.appcenter_todolist.model

import com.google.gson.annotations.SerializedName
//투두 생성 요청 DTO
data class AddTodoReq(
    @SerializedName("email")
    val email: String,
    @SerializedName("content")
    val content : String
)
