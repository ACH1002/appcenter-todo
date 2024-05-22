package com.example.appcenter_todolist.repository

import com.example.appcenter_todolist.model.CommonResponse
import com.example.appcenter_todolist.model.SignupMemberReq
import com.example.appcenter_todolist.network.APIService
import retrofit2.Response

class MemberRepositoryImpl(private val apiService: APIService) : MemberRepository {
    override suspend fun signUp(signupMemberReq: SignupMemberReq): Response<CommonResponse<Long>> {
        return apiService.signUp(signupMemberReq = signupMemberReq)
    }
}