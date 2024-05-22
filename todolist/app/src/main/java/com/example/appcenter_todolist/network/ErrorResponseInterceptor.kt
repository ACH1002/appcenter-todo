package com.example.appcenter_todolist.network

import com.example.appcenter_todolist.model.ErrorResponse
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class ErrorResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (!response.isSuccessful) {
            val errorBody = response.body?.string()
            if (errorBody != null) {
                val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                throw ApiException(errorResponse)
            }
        }
        return response
    }
}

class ApiException(val errorResponse: ErrorResponse) : IOException(errorResponse.message)

