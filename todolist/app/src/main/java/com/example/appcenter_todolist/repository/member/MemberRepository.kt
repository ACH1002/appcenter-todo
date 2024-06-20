package com.example.appcenter_todolist.repository.member

import com.example.appcenter_todolist.model.error.CommonResponse
import com.example.appcenter_todolist.model.member.LoginMemberReq
import com.example.appcenter_todolist.model.member.MemberResponse
import com.example.appcenter_todolist.model.member.SignupMemberReq
import com.example.appcenter_todolist.model.member.UpdateMemberReq
import retrofit2.Response

interface MemberRepository {
    suspend fun signUp(
        signupMemberReq: SignupMemberReq
    ): Response<CommonResponse<Void>>

    suspend fun login(
        loginMemberReq: LoginMemberReq
    ): Response<CommonResponse<Void>>

    suspend fun getMemberInfo(): Response<CommonResponse<MemberResponse>>

    suspend fun getMemberByNickname(
        nickname: String
    ): Response<CommonResponse<MemberResponse>>

    suspend fun getRandomMembers(): Response<CommonResponse<List<MemberResponse>>>

    suspend fun deleteMember(): Response<CommonResponse<Void>>

    suspend fun updateMember(
        updateMemberReq: UpdateMemberReq
    ): Response<CommonResponse<MemberResponse>>
}