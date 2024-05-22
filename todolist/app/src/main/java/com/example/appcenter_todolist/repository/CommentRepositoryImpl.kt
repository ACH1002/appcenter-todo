package com.example.appcenter_todolist.repository

import com.example.appcenter_todolist.model.AddCommentReq
import com.example.appcenter_todolist.model.CommentResponse
import com.example.appcenter_todolist.model.CommonResponse
import com.example.appcenter_todolist.model.UpdateCommentReq
import com.example.appcenter_todolist.network.APIService
import retrofit2.Response

class CommentRepositoryImpl(private val apiService: APIService) : CommentRepository {
    override suspend fun addCommentToTodo(
        memberId: Long,
        todoId: Long,
        addCommentReq: AddCommentReq
    ): Response<CommonResponse<Long>> {
        return apiService.addCommentToTodo(memberId = memberId, todoId = todoId, addCommentReq = addCommentReq)
    }

    override suspend fun getCommentByTodo(
        todoId: Long,
        commentId: Long
    ): Response<CommonResponse<CommentResponse>> {
        return apiService.getCommentByTodo(todoId = todoId, commentId = commentId)
    }

    override suspend fun deleteCommentByTodo(
        todoId: Long,
        commentId: Long
    ): Response<CommonResponse<Long>> {
        return apiService.deleteCommentByTodo(todoId = todoId, commentId = commentId)
    }

    override suspend fun updateCommentByTodo(
        todoId: Long,
        commentId: Long,
        updateCommentReq: UpdateCommentReq
    ): Response<CommonResponse<Long>> {
        return apiService.updateCommentByTodo(todoId = todoId, commentId = commentId, updateCommentReq = updateCommentReq)
    }

    override suspend fun getCommentsByTodo(
        memberId: Long,
        todoId: Long,
    ): Response<CommonResponse<List<CommentResponse>>> {
        return apiService.getCommentsByTodo(memberId, todoId)
    }
}