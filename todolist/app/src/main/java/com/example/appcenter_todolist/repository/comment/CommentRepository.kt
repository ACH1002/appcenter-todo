package com.example.appcenter_todolist.repository.comment

import com.example.appcenter_todolist.model.comment.AddCommentReq
import com.example.appcenter_todolist.model.comment.CommentResponse
import com.example.appcenter_todolist.model.comment.UpdateCommentReq
import com.example.appcenter_todolist.model.error.CommonResponse
import retrofit2.Response

interface CommentRepository {
    suspend fun addCommentToTodo(
        todoId: Long,
        addCommentReq: AddCommentReq
    ): Response<CommonResponse<CommentResponse>>



    suspend fun deleteCommentByTodo(
        commentId: Long
    ): Response<CommonResponse<Void>>

    suspend fun updateCommentByTodo(
        commentId: Long,
        updateCommentReq: UpdateCommentReq
    ): Response<CommonResponse<CommentResponse>>

    suspend fun getCommentsByTodo(
        todoId: Long,
    ): Response<CommonResponse<List<CommentResponse>>>
}