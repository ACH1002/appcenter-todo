package com.example.appcenter_todolist.model.member

import com.google.gson.annotations.SerializedName

data class UpdateMemberReq(
    @SerializedName("password")
    val password : String,
    @SerializedName("nickname")
    val nickname : String,
)
