package com.example.appcenter_todolist.repository

import com.example.appcenter_todolist.model.todo.AddTodoReq
import com.example.appcenter_todolist.model.CommonResponse
import com.example.appcenter_todolist.model.todo.TodoResponse
import com.example.appcenter_todolist.model.todo.UpdateTodoReq
import retrofit2.Response

interface TodoRepository {
    suspend fun getTodos(memberId : Long): Response<CommonResponse<List<TodoResponse>>>

    suspend fun addTodo(
        memberId : Long,
        addTodoReq: AddTodoReq
    ): Response<CommonResponse<TodoResponse>>

    suspend fun deleteTodoById(
        todoId: Long
    ): Response<CommonResponse<Long>>

    suspend fun updateTodoById(
        todoId: Long,
        updateTodoReq: UpdateTodoReq
    ): Response<CommonResponse<TodoResponse>>

    suspend fun completeTodoById(
        todoId: Long
    ): Response<CommonResponse<TodoResponse>>

}