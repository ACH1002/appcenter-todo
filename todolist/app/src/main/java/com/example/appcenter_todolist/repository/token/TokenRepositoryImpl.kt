package com.example.appcenter_todolist.repository.token

import com.example.appcenter_todolist.datastore.DataStoreManager
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
}