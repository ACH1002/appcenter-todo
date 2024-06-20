package com.example.appcenter_todolist.repository.token

import kotlinx.coroutines.flow.first

interface TokenRepository {
    suspend fun getToken() : String?

    suspend fun saveToken(token: String)

    suspend fun clearToken()

}