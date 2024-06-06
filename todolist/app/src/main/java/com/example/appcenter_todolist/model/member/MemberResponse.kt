package com.example.appcenter_todolist.model.member

import com.google.gson.annotations.SerializedName

data class MemberResponse(
    @SerializedName("id")
    val id : Long,
    @SerializedName("email")
    val email : String,
    @SerializedName("nickname")
    val nickname : String,
)
