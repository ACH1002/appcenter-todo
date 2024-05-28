package com.example.appcenter_todolist.model.comment

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class CommentResponse(
    @SerializedName("commentId")
    val commentId : Long,
    @SerializedName("content")
    val content : String,
    @SerializedName("deleted")
    val deleted : Boolean,
    @SerializedName("nickname")
    val nickname : String,
    @SerializedName("memberId")
    val memberId : Long,
    @SerializedName("createdTime")
    val createdTime : LocalDateTime,
    @SerializedName("modifiedTime")
    val modifiedTime : LocalDateTime
)
