package com.example.appcenter_todolist.model.todo

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.LocalDateTime

data class TodoResponse (
    @SerializedName("todoId")
    val id : Long,
    @SerializedName("content")
    val content : String,
    @SerializedName("deadLine")
    val deadLine : LocalDate,
    @SerializedName("completed")
    val completed : Boolean,
    @SerializedName("createdTime")
    val createdTime : LocalDateTime,
    @SerializedName("modifiedTime")
    val modifiedTime : LocalDateTime,
)