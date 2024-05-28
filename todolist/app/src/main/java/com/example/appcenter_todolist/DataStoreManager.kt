package com.example.appcenter_todolist

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {

    companion object {
        private val Context.dataStore by preferencesDataStore("user_prefs")
        val TOKEN_KEY = stringPreferencesKey("token_key")
        val MEMBER_ID_KEY = stringPreferencesKey("member_id_key")
    }

    val token: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[TOKEN_KEY]
        }
    val memberId: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[MEMBER_ID_KEY]
        }
    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun clearToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }

    suspend fun saveMemberId(memberId: String) {
        context.dataStore.edit { preferences ->
            preferences[MEMBER_ID_KEY] = memberId
        }
    }
    suspend fun clearMemberId() {
        context.dataStore.edit { preferences ->
            preferences.remove(MEMBER_ID_KEY)
        }
    }
}