package com.example.appcenter_todolist.repository

import com.example.appcenter_todolist.model.CommonResponse
import com.example.appcenter_todolist.model.bucket.AddBucketRequest
import com.example.appcenter_todolist.model.bucket.BucketResponse
import com.example.appcenter_todolist.model.bucket.UpdateBucketRequest
import com.example.appcenter_todolist.network.APIService
import retrofit2.Response

class BucketRepositoryImpl(private val apiService: APIService) : BucketRepository {
    override suspend fun getMyBuckets(): Response<CommonResponse<List<BucketResponse>>> {
        return apiService.getMyBuckets()
    }

    override suspend fun addBucket(addBucketRequest: AddBucketRequest): Response<CommonResponse<BucketResponse>> {
        return apiService.addBucket(addBucketRequest = addBucketRequest)
    }

    override suspend fun deleteBucket(bucketId: Long): Response<CommonResponse<Void>> {
        return apiService.deleteBucket(bucketId = bucketId)
    }

    override suspend fun updateBucket(
        bucketId: Long,
        updateBucketRequest: UpdateBucketRequest
    ): Response<CommonResponse<BucketResponse>> {
        return apiService.updateBucket(bucketId = bucketId, updateBucketRequest = updateBucketRequest)
    }

    override suspend fun completeBucket(bucketId: Long): Response<CommonResponse<BucketResponse>> {
        return apiService.completeBucket(bucketId = bucketId)
    }

    override suspend fun getBucketsByNickname(nickname: String): Response<CommonResponse<List<BucketResponse>>> {
        return apiService.getBucketsByNickname(nickname = nickname)
    }
}