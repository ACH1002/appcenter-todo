package com.example.appcenter_todolist.model

import com.google.gson.annotations.SerializedName

data class UpdateCommentReq(
    @SerializedName("content")
    val content: String
)
