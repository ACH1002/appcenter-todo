package com.example.appcenter_todolist.model

import com.google.gson.annotations.SerializedName

data class CommentResponse(
    @SerializedName("id")
    val id : Long,
    @SerializedName("content")
    val content : String,
    @SerializedName("deleted")
    val deleted : Boolean
)
