package com.example.appcenter_todolist.model

import com.google.gson.annotations.SerializedName

data class TodoResponse (
    @SerializedName("id")
    val id : Long,
    @SerializedName("content")
    val content : String,
    @SerializedName("completed")
    val completed : Boolean,
)