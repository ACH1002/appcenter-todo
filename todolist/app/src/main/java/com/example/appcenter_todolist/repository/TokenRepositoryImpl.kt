package com.example.appcenter_todolist.repository

import com.example.appcenter_todolist.DataStoreManager
import kotlinx.coroutines.flow.first

class TokenRepositoryImpl(private val dataStoreManager: DataStoreManager) : TokenRepository {
    override suspend fun getToken() : String? {
        return dataStoreManager.token.first()
    }

    override suspend fun saveToken(token: String) {
        dataStoreManager.saveToken(token)
    }

    override suspend fun clearToken() {
        dataStoreManager.clearToken()
    }

    override suspend fun getMember(): Long? {
        val memberIdString = dataStoreManager.memberId.first()
        return if (memberIdString.isNullOrEmpty()) null else memberIdString.toLong()
    }

    override suspend fun saveMemberId(memberId: Long) {
        dataStoreManager.saveMemberId(memberId = memberId.toString())
    }

    override suspend fun clearMemberId() {
        dataStoreManager.clearMemberId()
    }

}