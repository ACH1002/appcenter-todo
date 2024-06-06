package com.example.appcenter_todolist.repository

import com.example.appcenter_todolist.model.CommonResponse
import com.example.appcenter_todolist.model.member.LoginMemberReq
import com.example.appcenter_todolist.model.member.MemberResponse
import com.example.appcenter_todolist.model.member.SignupMemberReq
import com.example.appcenter_todolist.model.member.UpdateMemberReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH

interface MemberRepository {
    suspend fun signUp(
        signupMemberReq: SignupMemberReq
    ): Response<CommonResponse<Void>>

    suspend fun login(
        loginMemberReq: LoginMemberReq
    ): Response<CommonResponse<Void>>

    suspend fun getMemberInfo(): Response<CommonResponse<MemberResponse>>

    suspend fun deleteMember(): Response<CommonResponse<Void>>

    suspend fun updateMember(
        updateMemberReq: UpdateMemberReq
    ): Response<CommonResponse<MemberResponse>>
}