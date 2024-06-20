package com.example.appcenter_todolist.repository.bucket

import com.example.appcenter_todolist.model.error.CommonResponse
import com.example.appcenter_todolist.model.bucket.AddBucketRequest
import com.example.appcenter_todolist.model.bucket.BucketResponse
import com.example.appcenter_todolist.model.bucket.UpdateBucketRequest
import retrofit2.Response

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