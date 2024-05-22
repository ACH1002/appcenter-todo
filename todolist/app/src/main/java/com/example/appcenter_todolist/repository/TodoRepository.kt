package com.example.appcenter_todolist.repository

import com.example.appcenter_todolist.model.AddTodoReq
import com.example.appcenter_todolist.model.CommentResponse
import com.example.appcenter_todolist.model.CommonResponse
import com.example.appcenter_todolist.model.TodoResponse
import com.example.appcenter_todolist.model.UpdateTodoReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.Query

interface TodoRepository {
    suspend fun getTodos(): Response<CommonResponse<List<TodoResponse>>>

    suspend fun addTodo(
        addTodoReq: AddTodoReq
    ): Response<CommonResponse<Long>>

    suspend fun getTodoById(
        memberId: Long,
        todoId: Long
    ): Response<CommonResponse<TodoResponse>>

    suspend fun deleteTodoById(
        memberId: Long,
        todoId: Long
    ): Response<CommonResponse<Long>>

    suspend fun updateTodoById(
        memberId: Long,
        todoId: Long,
        updateTodoReq: UpdateTodoReq
    ): Response<CommonResponse<Long>>

    suspend fun completeTodoById(
        memberId: Long,
        todoId: Long
    ): Response<CommonResponse<Long>>

}