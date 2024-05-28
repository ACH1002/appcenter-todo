package com.example.appcenter_todolist.model.comment

import com.google.gson.annotations.SerializedName

data class AddCommentReq(
    @SerializedName("content")
    val content : String
)
