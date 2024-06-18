package com.example.appcenter_todolist.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcenter_todolist.model.member.LoginMemberReq
import com.example.appcenter_todolist.model.member.SignupMemberReq
import com.example.appcenter_todolist.network.ApiException
import com.example.appcenter_todolist.network.TokenExpirationEvent
import com.example.appcenter_todolist.repository.MemberRepository
import com.example.appcenter_todolist.repository.TokenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MemberViewModel(private val memberRepository: MemberRepository, private val tokenRepository: TokenRepository) : ViewModel() {
//    private val _signupEmail: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
//    val signupEmail = _signupEmail.asStateFlow()
//    private val _signupPassword: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
//    val signupPassword = _signupPassword.asStateFlow()
//    private val _signupNickName: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
//    val signupNickName = _signupNickName.asStateFlow()
    private val _signupSuccess: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val signupSuccess = _signupSuccess.asStateFlow()

    private val _loginSuccess: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val loginSuccess = _loginSuccess.asStateFlow()

    fun signup(
        signupMemberReq: SignupMemberReq
    ){
        viewModelScope.launch {
            try {
                val response = memberRepository.signUp(signupMemberReq = signupMemberReq)
                if (response.isSuccessful){
                    val signupMember = response.body() ?: throw Exception("회원가입 정보가 비어있습니다.")
                    _signupSuccess.update { true }
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
                    _loginSuccess.update { true }
                    TokenExpirationEvent.expired.postValue(false)

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

    fun clearLogin(){
        _loginSuccess.update { null }
    }
    fun clearSignUp(){
//        _signupEmail.update { TextFieldValue("") }
//        _signupPassword.update { TextFieldValue("") }
//        _signupNickName.update { TextFieldValue("") }
        _signupSuccess.update { null }

    }
}