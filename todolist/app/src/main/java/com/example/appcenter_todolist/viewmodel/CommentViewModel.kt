package com.example.appcenter_todolist.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcenter_todolist.model.comment.AddCommentReq
import com.example.appcenter_todolist.model.comment.CommentResponse
import com.example.appcenter_todolist.model.comment.UpdateCommentReq
import com.example.appcenter_todolist.model.member.SignupMemberReq
import com.example.appcenter_todolist.model.todo.TodoResponse
import com.example.appcenter_todolist.network.ApiException
import com.example.appcenter_todolist.repository.CommentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class CommentListState {
    object Loading : CommentListState()
    data class Success(val comments: List<CommentResponse>) : CommentListState()
    data class Error(val message: String) : CommentListState()
}

class CommentViewModel(private val commentRepository: CommentRepository) : ViewModel() {

    private val _commentListState: MutableStateFlow<CommentListState> = MutableStateFlow(CommentListState.Loading)
    val commentListState = _commentListState.asStateFlow()


    fun fetchComments(
        todoId: Long
    ){
        viewModelScope.launch {
            try {
                val response = commentRepository.getCommentsByTodo(todoId = todoId)
                if (response.isSuccessful){
                    val comments = response.body() ?: throw Exception("comment 정보가 비어있습니다.")
                    _commentListState.update { CommentListState.Success(comments.response) }
                } else {
                    throw Exception(response.errorBody()?.string())
                }
            } catch (e: ApiException) {
                Log.e("fetchComments 실패 원인", e.errorResponse.message)
            } catch (e: Exception) {
                Log.e("fetchComments 실패 원인", e.message.toString())
            } catch (e: RuntimeException){
                Log.e("fetchComments 시간초과", e.message.toString())
            }
        }
    }

    fun addCommentByTodo(
        todoId: Long,
        addCommentReq: AddCommentReq
    ){
        viewModelScope.launch {
            try {
                val response = commentRepository.addCommentToTodo(todoId = todoId, addCommentReq = addCommentReq)
                if (response.isSuccessful){
                    val addedComment = response.body() ?: throw Exception("추가한 Comment 정보가 비어있습니다.")
                    fetchComments(todoId = todoId)
                } else {
                    throw Exception(response.errorBody()?.string())
                }
            } catch (e: ApiException) {
                Log.e("addCommentByTodo 실패 원인", e.errorResponse.message)
            } catch (e: Exception) {
                Log.e("addCommentByTodo 실패 원인", e.message.toString())
            } catch (e: RuntimeException){
                Log.e("addCommentByTodo 시간초과", e.message.toString())
            }
        }
    }

    fun updateCommentByTodo(
        todoId: Long,
        commentId : Long,
        updateCommentReq: UpdateCommentReq
    ){
        viewModelScope.launch {
            try {
                val response = commentRepository.updateCommentByTodo(commentId = commentId, updateCommentReq = updateCommentReq)
                if (response.isSuccessful){
                    val updatedComment = response.body() ?: throw Exception("수정한 Comment 정보가 비어있습니다.")
                    fetchComments(todoId = todoId)
                } else {
                    throw Exception(response.errorBody()?.string())
                }
            } catch (e: ApiException) {
                Log.e("updateCommentByTodo 실패 원인", e.errorResponse.message)
            } catch (e: Exception) {
                Log.e("updateCommentByTodo 실패 원인", e.message.toString())
            } catch (e: RuntimeException){
                Log.e("updateCommentByTodo 시간초과", e.message.toString())
            }
        }
    }

    fun deleteCommentByTodo(
        todoId: Long,
        commentId: Long
    ){
        viewModelScope.launch {
            try {
                val response = commentRepository.deleteCommentByTodo(commentId = commentId)
                if (response.isSuccessful){
                    val deletedComment = response.body() ?: throw Exception("삭제한 Comment 정보가 비어있습니다.")
                    fetchComments(todoId = todoId)
                } else {
                    throw Exception(response.errorBody()?.string())
                }
            } catch (e: ApiException) {
                Log.e("deleteCommentByTodo 실패 원인", e.errorResponse.message)
            } catch (e: Exception) {
                Log.e("deleteCommentByTodo 실패 원인", e.message.toString())
            } catch (e: RuntimeException){
                Log.e("deleteCommentByTodo 시간초과", e.message.toString())
            }
        }
    }

}