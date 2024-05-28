package com.example.appcenter_todolist.model.member

import com.google.gson.annotations.SerializedName
//회원 가입 요청 DTO
data class SignupMemberReq(
    @SerializedName("email")
    val email : String,
    @SerializedName("password")
    val password : String,
    @SerializedName("nickname")
    val nickname : String,
)
