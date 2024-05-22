package com.example.appcenter_todolist.repository

import com.example.appcenter_todolist.model.AddCommentReq
import com.example.appcenter_todolist.model.CommentResponse
import com.example.appcenter_todolist.model.CommonResponse
import com.example.appcenter_todolist.model.UpdateCommentReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentRepository {
    suspend fun addCommentToTodo(
        memberId: Long,
        todoId: Long,
        addCommentReq: AddCommentReq
    ): Response<CommonResponse<Long>>

    suspend fun getCommentByTodo(
        todoId: Long,
        commentId: Long
    ): Response<CommonResponse<CommentResponse>>

    suspend fun deleteCommentByTodo(
        todoId: Long,
        commentId: Long
    ): Response<CommonResponse<Long>>

    suspend fun updateCommentByTodo(
        todoId: Long,
        commentId: Long,
        updateCommentReq: UpdateCommentReq
    ): Response<CommonResponse<Long>>

    suspend fun getCommentsByTodo(
        memberId: Long,
        todoId: Long,
    ): Response<CommonResponse<List<CommentResponse>>>
}