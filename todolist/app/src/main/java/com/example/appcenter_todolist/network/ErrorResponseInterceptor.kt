package com.example.appcenter_todolist.network

import com.example.appcenter_todolist.model.error.ErrorResponse
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class ErrorResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (!response.isSuccessful) {
            val errorBody = response.peekBody(Long.MAX_VALUE).string()
            if (errorBody.isNotEmpty()) {
                val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                if (errorResponse != null) {
                    throw ApiException(errorResponse)
                } else {
                    throw IOException("Unknown error occurred")
                }
            } else {
                throw IOException("Unknown error occurred")
            }
        }
        return response
    }
}

class ApiException(val errorResponse: ErrorResponse) : IOException(errorResponse.message)

