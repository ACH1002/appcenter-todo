package com.example.appcenter_todolist.model.todo

import com.google.gson.annotations.SerializedName

//투두 생성 요청 DTO
data class AddTodoReq(
    @SerializedName("content")
    val content : String,
    @SerializedName("deadLine")
    val deadLine : String
)
