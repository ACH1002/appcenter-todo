package com.example.appcenter_todolist.repository.member

import com.example.appcenter_todolist.model.error.CommonResponse
import com.example.appcenter_todolist.model.member.LoginMemberReq
import com.example.appcenter_todolist.model.member.MemberResponse
import com.example.appcenter_todolist.model.member.SignupMemberReq
import com.example.appcenter_todolist.model.member.UpdateMemberReq
import com.example.appcenter_todolist.network.APIService
import retrofit2.Response

class MemberRepositoryImpl(private val apiService: APIService) : MemberRepository {
    override suspend fun signUp(signupMemberReq: SignupMemberReq): Response<CommonResponse<Void>> {
        return apiService.signUp(signupMemberReq = signupMemberReq)
    }

    override suspend fun login(loginMemberReq: LoginMemberReq): Response<CommonResponse<Void>> {
        return apiService.login(loginMemberReq = loginMemberReq)
    }

    override suspend fun getMemberInfo(): Response<CommonResponse<MemberResponse>> {
        return apiService.getMemberInfo()
    }

    override suspend fun getMemberByNickname(
        nickname: String
    ): Response<CommonResponse<MemberResponse>> {
        return apiService.getMemberInfoByNickname(nickname = nickname)
    }

    override suspend fun getRandomMembers(): Response<CommonResponse<List<MemberResponse>>> {
        return apiService.getRandomMembers()
    }

    override suspend fun deleteMember(): Response<CommonResponse<Void>> {
        return apiService.deleteMember()
    }

    override suspend fun updateMember(updateMemberReq: UpdateMemberReq): Response<CommonResponse<MemberResponse>> {
        return apiService.updateMember(updateMemberReq = updateMemberReq)
    }
}