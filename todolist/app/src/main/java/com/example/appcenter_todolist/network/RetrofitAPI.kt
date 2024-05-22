package com.example.appcenter_todolist.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.component.KoinComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitAPI : KoinComponent {

    private const val BASE_URL = "http://na2ru2.me:5152"

//    private val tokenManager: TokenManager by inject()


    private val okHttpClient: OkHttpClient by lazy {
//        val context: Context = get()

        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(ErrorResponseInterceptor())
//            .addInterceptor(TokenInterceptor(tokenManager))
//            .addInterceptor(TokenExpirationInterceptor(context = context))
            .build()
    }


    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    val apiService: APIService by lazy {
        retrofit.create(APIService::class.java)
    }
}