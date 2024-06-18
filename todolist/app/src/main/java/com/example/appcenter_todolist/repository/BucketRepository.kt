package com.example.appcenter_todolist.repository

import com.example.appcenter_todolist.model.CommonResponse
import com.example.appcenter_todolist.model.bucket.AddBucketRequest
import com.example.appcenter_todolist.model.bucket.BucketResponse
import com.example.appcenter_todolist.model.bucket.UpdateBucketRequest
import com.example.appcenter_todolist.model.todo.AddTodoReq
import com.example.appcenter_todolist.model.todo.TodoResponse
import com.example.appcenter_todolist.model.todo.UpdateTodoReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BucketRepository {

    suspend fun getMyBuckets() : Response<CommonResponse<List<BucketResponse>>>

    suspend fun addBucket(
        addBucketRequest: AddBucketRequest
    ) : Response<CommonResponse<BucketResponse>>

    suspend fun deleteBucket(
        bucketId: Long
    ) : Response<CommonResponse<Void>>

    suspend fun updateBucket(
        bucketId: Long,
        updateBucketRequest: UpdateBucketRequest
    ) : Response<CommonResponse<BucketResponse>>

    suspend fun completeBucket(
        bucketId: Long
    ): Response<CommonResponse<BucketResponse>>

    suspend fun getBucketsByNickname(
        nickname: String
    ) : Response<CommonResponse<List<BucketResponse>>>
}