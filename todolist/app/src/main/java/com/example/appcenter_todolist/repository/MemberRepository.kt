package com.example.appcenter_todolist.repository

import com.example.appcenter_todolist.model.CommonResponse
import com.example.appcenter_todolist.model.SignupMemberReq
import retrofit2.Response

interface MemberRepository {
    suspend fun signUp(
        signupMemberReq: SignupMemberReq
    ): Response<CommonResponse<Long>>
}