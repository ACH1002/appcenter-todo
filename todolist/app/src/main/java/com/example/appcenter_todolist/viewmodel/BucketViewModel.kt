package com.example.appcenter_todolist.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcenter_todolist.model.bucket.AddBucketRequest
import com.example.appcenter_todolist.model.bucket.BucketResponse
import com.example.appcenter_todolist.model.bucket.UpdateBucketRequest
import com.example.appcenter_todolist.model.member.MemberResponse
import com.example.appcenter_todolist.network.ApiException
import com.example.appcenter_todolist.repository.bucket.BucketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class BucketListState {
    object Loading : BucketListState()
    data class Success(val buckets: List<BucketResponse>) : BucketListState()
    data class Error(val message: String) : BucketListState()
}

sealed class BucketState {
    object Loading : BucketState()
    data class Success(val bucket: BucketResponse) : BucketState()
    data class Error(val message: String) : BucketState()
}

class BucketViewModel(private val bucketRepository: BucketRepository) : ViewModel() {
    private val _bucketListState: MutableStateFlow<BucketListState> = MutableStateFlow(BucketListState.Loading)
    val bucketListState = _bucketListState.asStateFlow()

    private val _selectedMemberBucketListState: MutableStateFlow<BucketListState> = MutableStateFlow(BucketListState.Loading)
    val selectedMemberBucketListState = _selectedMemberBucketListState.asStateFlow()

    private val _searchedMemberBucketListState: MutableStateFlow<BucketListState> = MutableStateFlow(BucketListState.Loading)
    val searchedMemberBucketListState = _searchedMemberBucketListState.asStateFlow()

    private val _selectedBucketState: MutableStateFlow<BucketState> = MutableStateFlow(BucketState.Loading)
    val selectedBucketState = _selectedBucketState.asStateFlow()

    private val _selectedAnyoneBucketState: MutableStateFlow<BucketState> = MutableStateFlow(BucketState.Loading)
    val selectedAnyoneBucketState = _selectedAnyoneBucketState.asStateFlow()

    private val _addBucketState: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val addBucketState = _addBucketState.asStateFlow()



    init {
        // 동기화 로직을 init 블록에 추가
        viewModelScope.launch {
            combine(
                bucketListState,
                selectedBucketState
            ) { bucketListState, selectedBucketState ->
                Pair(bucketListState, selectedBucketState)
            }.collect { (bucketListState, selectedBucketState) ->
                if (bucketListState is BucketListState.Success && selectedBucketState is BucketState.Success) {
                    val updatedSelectedBucket = bucketListState.buckets.find { it.bucketId == selectedBucketState.bucket.bucketId }
                    if (updatedSelectedBucket != null) {
                        _selectedBucketState.update { BucketState.Success(bucket = updatedSelectedBucket) }
                    } else {
                        _selectedBucketState.update { BucketState.Error("Selected todo not found in the updated list") }
                    }
                }
            }
        }
    }

    fun fetchBucketList() {
        viewModelScope.launch {
            try {
                val response = bucketRepository.getMyBuckets()
                if (response.isSuccessful){
                    val newBucketList = response.body() ?: throw Exception("내 BucketList 정보가 비어있습니다.")
                    _bucketListState.update { BucketListState.Success(newBucketList.response) }
                } else {
                    throw Exception(response.errorBody()?.string())
                }
            } catch (e: ApiException) {
                Log.e("fetchBucketList 실패 원인", e.errorResponse.message)
                _bucketListState.update { BucketListState.Error(e.errorResponse.message) }
            } catch (e: Exception) {
                Log.e("fetchBucketList 실패 원인", e.message.toString())
                _bucketListState.update { BucketListState.Error(e.message.toString()) }
            } catch (e: RuntimeException){
                Log.e("fetchBucketList 시간초과", e.message.toString())
                _bucketListState.update { BucketListState.Error(e.message.toString()) }
            }
        }
    }

    fun getBucketListByMember(selectedMember: MemberResponse) {
        viewModelScope.launch {
            try {
                val response = bucketRepository.getBucketsByNickname(nickname = selectedMember.nickname)
                if (response.isSuccessful){
                    val bucketListByNickname = response.body() ?: throw Exception("BucketList 정보가 비어있습니다.")
                    _selectedMemberBucketListState.update { BucketListState.Success(bucketListByNickname.response) }
                } else {
                    _selectedMemberBucketListState.update { BucketListState.Error(response.errorBody().toString()) }
                    throw Exception(response.errorBody()?.string())
                }
            } catch (e: ApiException) {
                Log.e("fetchBucketList 실패 원인", e.errorResponse.message)
                _selectedMemberBucketListState.update { BucketListState.Error(e.errorResponse.message) }
            } catch (e: Exception) {
                _selectedMemberBucketListState.update { BucketListState.Error(e.message.toString()) }
                Log.e("fetchBucketList 실패 원인", e.message.toString())
            } catch (e: RuntimeException){
                _selectedMemberBucketListState.update { BucketListState.Error(e.message.toString()) }
                Log.e("fetchBucketList 시간초과", e.message.toString())
            }
        }
    }

    fun searchBucketByMember(nickname: String) {
        viewModelScope.launch {
            try {
                val response = bucketRepository.getBucketsByNickname(nickname = nickname)
                if (response.isSuccessful){
                    val bucketListByNickname = response.body() ?: throw Exception("검색한 BucketList 정보가 비어있습니다.")
                    _searchedMemberBucketListState.update { BucketListState.Success(bucketListByNickname.response) }
                } else {
                    _searchedMemberBucketListState.update { BucketListState.Error(response.errorBody().toString()) }
                    throw Exception(response.errorBody()?.string())
                }
            } catch (e: ApiException) {
                Log.e("searchBucketByMember 실패 원인", e.errorResponse.message)
                _searchedMemberBucketListState.update { BucketListState.Error(e.errorResponse.message) }
            } catch (e: Exception) {
                _searchedMemberBucketListState.update { BucketListState.Error(e.message.toString()) }
                Log.e("searchBucketByMember 실패 원인", e.message.toString())
            } catch (e: RuntimeException){
                _searchedMemberBucketListState.update { BucketListState.Error(e.message.toString()) }
                Log.e("searchBucketByMember 시간초과", e.message.toString())
            }
        }
    }

    fun addBucket(addBucketRequest: AddBucketRequest) {
        viewModelScope.launch {
            try {
                val response = bucketRepository.addBucket(addBucketRequest = addBucketRequest)
                if (response.isSuccessful) {
                    val newBucket = response.body() ?: throw Exception("추가한 Bucket의 정보가 비어있습니다.")
                    fetchBucketList()
                    _addBucketState.update { true }
                } else {
                    _addBucketState.update { false }
                    throw Exception(response.errorBody()?.string())
                }
            } catch (e: ApiException) {
                Log.e("addBucket 실패 원인", e.errorResponse.message)
                _addBucketState.update { false }
                _bucketListState.update { BucketListState.Error(e.errorResponse.message) }
            } catch (e: Exception) {
                Log.e("addBucket 실패 원인", e.message.toString())
                _addBucketState.update { false }
                _bucketListState.update { BucketListState.Error(e.message.toString()) }
            } catch (e: RuntimeException) {
                Log.e("addBucket 시간초과", e.message.toString())
                _addBucketState.update { false }
                _bucketListState.update { BucketListState.Error(e.message.toString()) }
            }
        }
    }


    fun deleteBucket(
        bucketId: Long,
    ){
        viewModelScope.launch {
            try {
                val response = bucketRepository.deleteBucket(bucketId = bucketId)
                if (response.isSuccessful){
                    val deletedBucket = response.body() ?: throw Exception("삭제한 Bucket의 정보가 비어있습니다.")
                    fetchBucketList()
                } else {
                    throw Exception(response.errorBody()?.string())
                }
            } catch (e: ApiException) {
                Log.e("deleteBucket 실패 원인", e.errorResponse.message)
                _bucketListState.update { BucketListState.Error(e.errorResponse.message) }
            } catch (e: Exception) {
                Log.e("deleteBucket 실패 원인", e.message.toString())
                _bucketListState.update { BucketListState.Error(e.message.toString()) }
            } catch (e: RuntimeException){
                Log.e("deleteBucket 시간초과", e.message.toString())
                _bucketListState.update { BucketListState.Error(e.message.toString()) }
            }
        }
    }

    fun updateBucket(
        bucketId: Long,
        updateBucketRequest: UpdateBucketRequest
    ){
        viewModelScope.launch {
            try {
                val response = bucketRepository.updateBucket(bucketId = bucketId, updateBucketRequest = updateBucketRequest)
                if (response.isSuccessful){
                    val updatedBucket = response.body() ?: throw Exception("수정한 Bucket의 정보가 비어있습니다.")
                    fetchBucketList()
                } else {
                    throw Exception(response.errorBody()?.string())
                }
            } catch (e: ApiException) {
                Log.e("updateBucket 실패 원인", e.errorResponse.message)
                _bucketListState.update { BucketListState.Error(e.errorResponse.message) }
            } catch (e: Exception) {
                Log.e("updateBucket 실패 원인", e.message.toString())
                _bucketListState.update { BucketListState.Error(e.message.toString()) }
            } catch (e: RuntimeException){
                Log.e("updateBucket 시간초과", e.message.toString())
                _bucketListState.update { BucketListState.Error(e.message.toString()) }
            }
        }
    }

    fun completeBucket(
        bucketId: Long,
    ){
        viewModelScope.launch {
            try {
                val response = bucketRepository.completeBucket(bucketId = bucketId)
                if (response.isSuccessful){
                    val completedBucket = response.body() ?: throw Exception("완료한 Bucket의 정보가 비어있습니다.")
                    fetchBucketList()
                } else {
                    throw Exception(response.errorBody()?.string())
                }
            } catch (e: ApiException) {
                Log.e("completeBucket 실패 원인", e.errorResponse.message)
                _bucketListState.update { BucketListState.Error(e.errorResponse.message) }
            } catch (e: Exception) {
                Log.e("completeBucket 실패 원인", e.message.toString())
                _bucketListState.update { BucketListState.Error(e.message.toString()) }
            } catch (e: RuntimeException){
                Log.e("completeBucket 시간초과", e.message.toString())
                _bucketListState.update { BucketListState.Error(e.message.toString()) }
            }
        }
    }

    fun clearAddBucketState(){
        _addBucketState.update { null }
    }

    fun setSelectedBucketState(bucket: BucketResponse){
        _selectedBucketState.update {
            BucketState.Success(bucket = bucket)
        }
    }

    fun setSelectedAnyoneBucketState(bucket: BucketResponse){
        _selectedAnyoneBucketState.update {
            BucketState.Success(bucket = bucket)
        }
    }

    fun clearSelectedBucketState(){
        _selectedBucketState.update {
            BucketState.Loading
        }
    }

    fun clearSearchedMemberBucketListState(){
        _searchedMemberBucketListState.update {
            BucketListState.Loading
        }
    }
}