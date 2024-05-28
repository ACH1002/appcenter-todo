package com.example.appcenter_todolist.model.todo

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class UpdateTodoReq(
    @SerializedName("content")
    val content: String,
    @SerializedName("deadLine")
    val deadLine: LocalDate,
)
