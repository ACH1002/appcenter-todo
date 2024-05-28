package com.example.appcenter_todolist.repository

import com.example.appcenter_todolist.model.CommonResponse
import com.example.appcenter_todolist.model.member.LoginMemberReq
import com.example.appcenter_todolist.model.member.SignupMemberReq
import retrofit2.Response
import retrofit2.http.Body

interface MemberRepository {
    suspend fun signUp(
        signupMemberReq: SignupMemberReq
    ): Response<CommonResponse<Long>>

    suspend fun login(
        loginMemberReq: LoginMemberReq
    ): Response<CommonResponse<Void>>

}