package com.example.appcenter_todolist.repository

import com.example.appcenter_todolist.model.comment.AddCommentReq
import com.example.appcenter_todolist.model.comment.CommentResponse
import com.example.appcenter_todolist.model.CommonResponse
import com.example.appcenter_todolist.model.comment.UpdateCommentReq
import com.example.appcenter_todolist.network.APIService
import retrofit2.Response

class CommentRepositoryImpl(private val apiService: APIService) : CommentRepository {
    override suspend fun addCommentToTodo(
        todoId: Long,
        addCommentReq: AddCommentReq
    ): Response<CommonResponse<CommentResponse>> {
        return apiService.addCommentToTodo(todoId = todoId, addCommentReq = addCommentReq)
    }

    override suspend fun deleteCommentByTodo(
        commentId: Long
    ): Response<CommonResponse<CommentResponse>> {
        return apiService.deleteCommentByTodo(commentId = commentId)
    }

    override suspend fun updateCommentByTodo(
        commentId: Long,
        updateCommentReq: UpdateCommentReq
    ): Response<CommonResponse<CommentResponse>> {
        return apiService.updateCommentByTodo(commentId = commentId, updateCommentReq = updateCommentReq)
    }

    override suspend fun getCommentsByTodo(
        todoId: Long,
    ): Response<CommonResponse<List<CommentResponse>>> {
        return apiService.getCommentsByTodo(todoId = todoId)
    }
}