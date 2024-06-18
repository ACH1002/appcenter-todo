package com.example.appcenter_todolist.model.bucket

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class BucketResponse(
    @SerializedName("bucketId")
    val bucketId : Long,
    @SerializedName("content")
    val content : String,
    @SerializedName("deadLine")
    val deadLine : String,
    @SerializedName("completed")
    val completed : Boolean,
    @SerializedName("memberId")
    val memberId : Long,
    @SerializedName("createdTime")
    val createdTime : LocalDateTime,
    @SerializedName("modifiedTime")
    val modifiedTime : LocalDateTime
)
