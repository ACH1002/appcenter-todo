package com.example.appcenter_todolist.model.bucket

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class UpdateBucketRequest(
    @SerializedName("content")
    val content: String,
    @SerializedName("deadLine")
    val deadLine: String
)
