package com.example.appcenter_todolist.repository

import kotlinx.coroutines.flow.first

interface TokenRepository {
    suspend fun getToken() : String?

    suspend fun saveToken(token: String)

    suspend fun clearToken()

    suspend fun getMember() : Long?

    suspend fun saveMemberId(memberId: Long)

    suspend fun clearMemberId()
}