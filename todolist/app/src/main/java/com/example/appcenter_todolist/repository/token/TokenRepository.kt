package com.example.appcenter_todolist.repository.token

interface TokenRepository {
    suspend fun getToken() : String?

    suspend fun saveToken(token: String)

    suspend fun clearToken()

}