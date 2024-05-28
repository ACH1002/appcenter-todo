package com.example.appcenter_todolist.model.member

import com.google.gson.annotations.SerializedName

data class LoginMemberReq(
    @SerializedName("email")
    val email : String,
    @SerializedName("password")
    val password : String
)
