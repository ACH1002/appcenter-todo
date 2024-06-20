package com.example.appcenter_todolist.repository.todo

import com.example.appcenter_todolist.model.error.CommonResponse
import com.example.appcenter_todolist.model.todo.AddTodoReq
import com.example.appcenter_todolist.model.todo.TodoResponse
import com.example.appcenter_todolist.model.todo.UpdateTodoReq
import retrofit2.Response

interface TodoRepository {
    suspend fun getTodosByBucket(
        bucketId: Long
    ): Response<CommonResponse<List<TodoResponse>>>


    suspend fun addTodo(
        bucketId: Long,
        addTodoReq: AddTodoReq
    ): Response<CommonResponse<TodoResponse>>

    suspend fun deleteTodoById(
        todoId: Long
    ): Response<CommonResponse<Void>>

    suspend fun updateTodoById(
        todoId: Long,
        updateTodoReq: UpdateTodoReq
    ): Response<CommonResponse<TodoResponse>>

    suspend fun completeTodoById(
        todoId: Long
    ): Response<CommonResponse<TodoResponse>>

}