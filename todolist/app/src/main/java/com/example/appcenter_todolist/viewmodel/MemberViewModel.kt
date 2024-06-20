package com.example.appcenter_todolist.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcenter_todolist.model.member.LoginMemberReq
import com.example.appcenter_todolist.model.member.MemberResponse
import com.example.appcenter_todolist.model.member.SignupMemberReq
import com.example.appcenter_todolist.network.ApiException
import com.example.appcenter_todolist.network.TokenExpirationEvent
import com.example.appcenter_todolist.repository.member.MemberRepository
import com.example.appcenter_todolist.repository.token.TokenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch



sealed class SignUpState {
    object Loading : SignUpState()
    data class Success(val message: String) : SignUpState()
    data class Error(val message: String) : SignUpState()
}
sealed class MyInfoState {
    object Loading : MyInfoState()
    data class Success(val myInfo: MemberResponse) : MyInfoState()
    data class Error(val message: String) : MyInfoState()
}

sealed class SelectedMemberState {
    object Loading : SelectedMemberState()
    data class Success(val selectedMember: MemberResponse) : SelectedMemberState()
    data class Error(val message: String) : SelectedMemberState()
}

sealed class RandomMembersState {
    object Loading : RandomMembersState()
    data class Success(val randomMembers: List<MemberResponse>) : RandomMembersState()
    data class Error(val message: String) : RandomMembersState()
}

class MemberViewModel(private val memberRepository: MemberRepository, private val tokenRepository: TokenRepository) : ViewModel() {
//    private val _signupEmail: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
//    val signupEmail = _signupEmail.asStateFlow()
//    private val _signupPassword: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
//    val signupPassword = _signupPassword.asStateFlow()
//    private val _signupNickName: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
//    val signupNickName = _signupNickName.asStateFlow()
    private val _signupSuccess: MutableStateFlow<SignUpState> = MutableStateFlow(SignUpState.Loading)
    val signupSuccess = _signupSuccess.asStateFlow()

    private val _loginSuccess: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val loginSuccess = _loginSuccess.asStateFlow()

    private val _loginState = MutableStateFlow<Boolean>(false)
    val loginState = _loginState.asStateFlow()

    private val _myInfoState: MutableStateFlow<MyInfoState> = MutableStateFlow(MyInfoState.Loading)
    val myInfoState = _myInfoState.asStateFlow()

    private val _searchedMemberInfo: MutableStateFlow<MyInfoState> = MutableStateFlow(MyInfoState.Loading)
    val searchedMemberInfo = _searchedMemberInfo.asStateFlow()

    private val _selectedMemberState: MutableStateFlow<SelectedMemberState> = MutableStateFlow(SelectedMemberState.Loading)
    val selectedMemberState = _selectedMemberState.asStateFlow()

    private val _randomMembersState: MutableStateFlow<RandomMembersState> = MutableStateFlow(RandomMembersState.Loading)
    val randomMembersState = _randomMembersState.asStateFlow()

    private val _hasFetchedRandomMembers: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val hasFetchedRandomMembers = _hasFetchedRandomMembers.asStateFlow()

    private val _isCheckValidity: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isCheckValidity = _isCheckValidity.asStateFlow()

    private val _searchedNickname: MutableStateFlow<String?> = MutableStateFlow(null)
    val searchedNickname = _searchedNickname.asStateFlow()

    fun checkTokenValidity() {
        viewModelScope.launch {
            try {
                val response = memberRepository.getMemberInfo()
                if (response.isSuccessful){
                    Log.d("checkTokenValidity", response.toString())
                    _loginState.update { true }
                    _loginSuccess.update { true }
                    TokenExpirationEvent.expired.postValue(false)

                } else {
                    _loginState.update { false }
                    _loginSuccess.update { false }
                    tokenRepository.clearToken()
                    TokenExpirationEvent.expired.postValue(true)

                    throw Exception(response.errorBody()?.string())
                }
            } catch (e: ApiException) {
                _loginState.update { false }
                _loginSuccess.update { false }
                Log.e("checkTokenValidity 실패 원인", e.errorResponse.message)
            } catch (e: Exception) {
                _loginState.update { false }
                _loginSuccess.update { false }
                Log.e("checkTokenValidity 실패 원인", e.message.toString())
            } catch (e: RuntimeException){
                _loginState.update { false }
                _loginSuccess.update { false }
                Log.e("checkTokenValidity 시간초과", e.message.toString())
            }
        }
    }

    fun signup(
        signupMemberReq: SignupMemberReq
    ){
        viewModelScope.launch {
            try {
                val response = memberRepository.signUp(signupMemberReq = signupMemberReq)
                if (response.isSuccessful){
                    val signupMember = response.body() ?: throw Exception("회원가입 정보가 비어있습니다.")
                    _signupSuccess.update { SignUpState.Success(message = "회원가입 성공") }
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

                    _loginState.update { true }
                    _loginSuccess.update { true }
                    TokenExpirationEvent.expired.postValue(false)

                    Log.d("login 성공", "login 멤버 토큰: $loginToken")
                } else {
                    _loginState.update { false }
                    TokenExpirationEvent.expired.postValue(true)
                    throw Exception(response.errorBody()?.string())
                }
            } catch (e: ApiException) {
                _loginState.update { false }
                TokenExpirationEvent.expired.postValue(true)
                Log.e("login 실패 원인", e.errorResponse.message)
            } catch (e: Exception) {
                _loginState.update { false }
                TokenExpirationEvent.expired.postValue(true)
                Log.e("login 실패 원인", e.message.toString())
            } catch (e: RuntimeException){
                _loginState.update { false }
                TokenExpirationEvent.expired.postValue(true)
                Log.e("login 시간초과", e.message.toString())
            }
        }
    }

    fun getRandomMembers(
    ){
        viewModelScope.launch {
            try {
                val response = memberRepository.getRandomMembers()
                if (response.isSuccessful){
                    val randomMembers = response.body() ?: throw Exception("Member 정보가 비어있습니다.")
                    _randomMembersState.update { RandomMembersState.Success(randomMembers = randomMembers.response) }
                    setSelectedMember(randomMembers.response.first())
                } else {
                    _randomMembersState.update { RandomMembersState.Error(message = response.errorBody()?.string() ?: "원인 미상") }
                    throw Exception(response.errorBody()?.string())
                }
            } catch (e: ApiException) {
                _randomMembersState.update { RandomMembersState.Error(message = e.errorResponse.message) }
                Log.e("getRandomMembers 실패 원인", e.errorResponse.message)
            } catch (e: Exception) {
                _randomMembersState.update { RandomMembersState.Error(message = e.message.toString()) }
                Log.e("getRandomMembers 실패 원인", e.message.toString())
            } catch (e: RuntimeException){
                _randomMembersState.update { RandomMembersState.Error(message = e.message.toString()) }
                Log.e("getRandomMembers 시간초과", e.message.toString())
            }
        }
    }

    fun getMyInfo(
    ){
        viewModelScope.launch {
            try {
                val response = memberRepository.getMemberInfo()
                if (response.isSuccessful){
//                    val loginToken = response.headers()["authorization"] ?: throw Exception("login 정보가 비어있습니다.")
//                    tokenRepository.saveToken(token = loginToken)
//                    _loginSuccess.update { true }
//                    TokenExpirationEvent.expired.postValue(false)

//                    Log.d("login 성공", "login 멤버 토큰: $loginToken")
                    val myInfo = response.body() ?: throw Exception("Member 정보가 비어있습니다.")
                    _myInfoState.update { MyInfoState.Success(myInfo = myInfo.response) }
                } else {
                    throw Exception(response.errorBody()?.string())
                }
            } catch (e: ApiException) {
                Log.e("getMyInfo 실패 원인", e.errorResponse.message)
            } catch (e: Exception) {
                Log.e("getMyInfo 실패 원인", e.message.toString())
            } catch (e: RuntimeException){
                Log.e("getMyInfo 시간초과", e.message.toString())
            }
        }
    }

    fun getMemberByNickname(
        nickname: String
    ){
        viewModelScope.launch {
            try {
                val response = memberRepository.getMemberByNickname(nickname)
                if (response.isSuccessful){
                    val memberInfo = response.body() ?: throw Exception("Member 정보가 비어있습니다.")
                    _searchedMemberInfo.update { MyInfoState.Success(myInfo = memberInfo.response) }
                } else {
                    _searchedMemberInfo.update { MyInfoState.Error(message = response.errorBody().toString()) }
                    throw Exception(response.errorBody()?.string())
                }
            } catch (e: ApiException) {
                _searchedMemberInfo.update { MyInfoState.Error(message = e.errorResponse.message) }
                Log.e("getMemberByNickname 실패 원인", e.errorResponse.message)
            } catch (e: Exception) {
                _searchedMemberInfo.update { MyInfoState.Error(message = e.message.toString()) }
                Log.e("getMemberByNickname 실패 원인", e.message.toString())
            } catch (e: RuntimeException){
                _searchedMemberInfo.update { MyInfoState.Error(message = e.message.toString()) }
                Log.e("getMemberByNickname 시간초과", e.message.toString())
            }
        }
    }

    fun getMemberInfoByNickname(
        nickname: String
    ){
        viewModelScope.launch {
            try {
                val response = memberRepository.getMemberInfo()
                if (response.isSuccessful){
//                    val loginToken = response.headers()["authorization"] ?: throw Exception("login 정보가 비어있습니다.")
//                    tokenRepository.saveToken(token = loginToken)
//                    _loginSuccess.update { true }
//                    TokenExpirationEvent.expired.postValue(false)

//                    Log.d("login 성공", "login 멤버 토큰: $loginToken")
                    val myInfo = response.body() ?: throw Exception("Member 정보가 비어있습니다.")
                    _myInfoState.update { MyInfoState.Success(myInfo = myInfo.response) }
                } else {
                    throw Exception(response.errorBody()?.string())
                }
            } catch (e: ApiException) {
                Log.e("getMyInfo 실패 원인", e.errorResponse.message)
            } catch (e: Exception) {
                Log.e("getMyInfo 실패 원인", e.message.toString())
            } catch (e: RuntimeException){
                Log.e("getMyInfo 시간초과", e.message.toString())
            }
        }
    }

    fun logout(){
        viewModelScope.launch {
            try {
                tokenRepository.clearToken()
                Log.d("logout 성공", "logout 성공")
                _loginState.update { false }
                _loginSuccess.update { false }
                TokenExpirationEvent.expired.postValue(true)

            } catch (e: ApiException) {
                Log.e("logout 실패 원인", e.errorResponse.message)
            } catch (e: Exception) {
                Log.e("logout 실패 원인", e.message.toString())
            } catch (e: RuntimeException){
                Log.e("logout 시간초과", e.message.toString())
            }
        }
    }

    fun setSelectedMember(member: MemberResponse){
        _selectedMemberState.update {
            SelectedMemberState.Success(selectedMember = member)
        }
    }

    fun clearSelectedMember(){
        _selectedMemberState.update {
            SelectedMemberState.Loading
        }
    }

    fun clearLogin(){
        _loginSuccess.update { null }
    }
    fun clearSignUp(){
//        _signupEmail.update { TextFieldValue("") }
//        _signupPassword.update { TextFieldValue("") }
//        _signupNickName.update { TextFieldValue("") }
        _signupSuccess.update { SignUpState.Loading }

    }

    fun hasFetchRandomMembers(){
        _hasFetchedRandomMembers.update {
            true
        }
    }

    fun isCheckValidity(){
        _isCheckValidity.update { true }
    }

    fun setSearchedNickname(nickname: String){
        _searchedNickname.update { nickname }
    }
    fun clearSearchedNickname(){
        _searchedNickname.update { null }
    }

}