package com.example.appcenter_todolist.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcenter_todolist.network.ApiException
import com.example.appcenter_todolist.network.TokenExpirationEvent
import com.example.appcenter_todolist.repository.BucketRepository
import com.example.appcenter_todolist.repository.MemberRepository
import com.example.appcenter_todolist.repository.TokenRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(private val bucketRepository: BucketRepository, private val tokenRepository: TokenRepository) : ViewModel() {

    private val _isTokenValid: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isTokenValid = _isTokenValid.asStateFlow()
    fun checkTokenValidity() {
        viewModelScope.launch {
            try {
                val response = bucketRepository.getMyBuckets()
                if (response.isSuccessful){
                    _isTokenValid.update { true }
                    Log.d("ddddd", "dddd")
                    TokenExpirationEvent.expired.postValue(false)

                } else {
                    _isTokenValid.update { false }
                    tokenRepository.clearToken()
                    TokenExpirationEvent.expired.postValue(true)

                    throw Exception(response.errorBody()?.string())
                }
            } catch (e: ApiException) {
                Log.e("checkTokenValidity 실패 원인", e.errorResponse.message)
            } catch (e: Exception) {
                Log.e("checkTokenValidity 실패 원인", e.message.toString())
            } catch (e: RuntimeException){
                Log.e("checkTokenValidity 시간초과", e.message.toString())
            }
        }
    }

    suspend fun getToken(): String? {
      return tokenRepository.getToken()
    }
}