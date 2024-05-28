package com.example.appcenter_todolist.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcenter_todolist.model.member.LoginMemberReq
import com.example.appcenter_todolist.model.member.SignupMemberReq
import com.example.appcenter_todolist.network.ApiException
import com.example.appcenter_todolist.repository.MemberRepository
import com.example.appcenter_todolist.repository.TokenRepository
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MemberViewModel(private val memberRepository: MemberRepository, private val tokenRepository: TokenRepository) : ViewModel() {


    fun signup(
        signupMemberReq: SignupMemberReq
    ){
        viewModelScope.launch {
            try {
                val response = memberRepository.signUp(signupMemberReq = signupMemberReq)
                if (response.isSuccessful){
                    val signupMember = response.body() ?: throw Exception("회원가입 정보가 비어있습니다.")
                    Log.d("signup 성공", "회원가입한 멤버 ID: ${signupMember.response}")
                } else {
                    throw Exception(response.errorBody()?.string())
                }
            } catch (e: ApiException) {
                Log.e("signup 실패 원인", e.errorResponse.message)
            } catch (e: Exception) {
                Log.e("signup 실패 원인", e.message.toString())
            } catch (e: RuntimeException){
                Log.e("signup 시간초과", e.message.toString())
            }
        }
    }

    fun login(
        loginMemberReq: LoginMemberReq
    ){
        viewModelScope.launch {
            try {
                val response = memberRepository.login(loginMemberReq = loginMemberReq)
                if (response.isSuccessful){
                    val loginToken = response.headers()["authorization"] ?: throw Exception("login 정보가 비어있습니다.")
                    tokenRepository.saveToken(token = loginToken)
                    Log.d("login 성공", "login 멤버 토큰: $loginToken")
                } else {
                    throw Exception(response.errorBody()?.string())
                }
            } catch (e: ApiException) {
                Log.e("login 실패 원인", e.errorResponse.message)
            } catch (e: Exception) {
                Log.e("login 실패 원인", e.message.toString())
            } catch (e: RuntimeException){
                Log.e("login 시간초과", e.message.toString())
            }
        }
    }

    fun logout(){
        viewModelScope.launch {
            try {
                tokenRepository.clearToken()
                Log.d("logout 성공", "logout 성공")
            } catch (e: ApiException) {
                Log.e("logout 실패 원인", e.errorResponse.message)
            } catch (e: Exception) {
                Log.e("logout 실패 원인", e.message.toString())
            } catch (e: RuntimeException){
                Log.e("logout 시간초과", e.message.toString())
            }
        }
    }
}