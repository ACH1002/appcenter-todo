package com.example.appcenter_todolist.network

import androidx.lifecycle.MutableLiveData
import com.example.appcenter_todolist.datastore.DataStoreManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(private val dataStoreManager: DataStoreManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            dataStoreManager.token.first()
        }

        val request: Request = if (token != null) {
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            chain.request()
        }

        val response = chain.proceed(request)

        if (response.code == 401) {
            runBlocking {
                TokenExpirationEvent.expired.postValue(true)

                dataStoreManager.clearToken()
            }
        }

        return response
    }
}

object TokenExpirationEvent {
    val expired = MutableLiveData<Boolean>(false)
}
