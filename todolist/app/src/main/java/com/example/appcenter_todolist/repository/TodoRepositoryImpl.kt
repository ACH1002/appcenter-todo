package com.example.appcenter_todolist.repository

import com.example.appcenter_todolist.model.AddTodoReq
import com.example.appcenter_todolist.model.CommonResponse
import com.example.appcenter_todolist.model.TodoResponse
import com.example.appcenter_todolist.model.UpdateTodoReq
import com.example.appcenter_todolist.network.APIService
import retrofit2.Response

class TodoRepositoryImpl(private val apiService: APIService) : TodoRepository {
    override suspend fun getTodos(): Response<CommonResponse<List<TodoResponse>>> {
        return apiService.getTodos()
    }

    override suspend fun addTodo(addTodoReq: AddTodoReq): Response<CommonResponse<Long>> {
        return apiService.addTodo(addTodoReq = addTodoReq)
    }

    override suspend fun getTodoById(
        memberId: Long,
        todoId: Long
    ): Response<CommonResponse<TodoResponse>> {
        return apiService.getTodoById(memberId = memberId, todoId = todoId)
    }

    override suspend fun deleteTodoById(
        memberId: Long,
        todoId: Long
    ): Response<CommonResponse<Long>> {
        return apiService.deleteTodoById(memberId = memberId, todoId = todoId)
    }

    override suspend fun updateTodoById(
        memberId: Long,
        todoId: Long,
        updateTodoReq: UpdateTodoReq
    ): Response<CommonResponse<Long>> {
        return apiService.updateTodoById(memberId = memberId, todoId = todoId, updateTodoReq = updateTodoReq)
    }

    override suspend fun completeTodoById(
        memberId: Long,
        todoId: Long
    ): Response<CommonResponse<Long>> {
        return apiService.completeTodoById(memberId = memberId, todoId = todoId)
    }
}