package com.example.appcenter_todolist.repository

import com.example.appcenter_todolist.model.todo.AddTodoReq
import com.example.appcenter_todolist.model.CommonResponse
import com.example.appcenter_todolist.model.todo.TodoResponse
import com.example.appcenter_todolist.model.todo.UpdateTodoReq
import com.example.appcenter_todolist.network.APIService
import retrofit2.Response

class TodoRepositoryImpl(private val apiService: APIService) : TodoRepository {
    override suspend fun getMyTodos(): Response<CommonResponse<List<TodoResponse>>> {
        return apiService.getMyTodos()
    }

    override suspend fun getTodosByNickname(nickname: String): Response<CommonResponse<List<TodoResponse>>> {
        return apiService.getTodosByNickname(nickname = nickname)
    }


    override suspend fun addTodo(addTodoReq: AddTodoReq): Response<CommonResponse<TodoResponse>> {
        return apiService.addTodo(addTodoReq = addTodoReq)
    }

    override suspend fun deleteTodoById(
        todoId: Long
    ): Response<CommonResponse<Void>> {
        return apiService.deleteTodoById(todoId = todoId)
    }

    override suspend fun updateTodoById(
        todoId: Long,
        updateTodoReq: UpdateTodoReq
    ): Response<CommonResponse<TodoResponse>> {
        return apiService.updateTodoById(todoId = todoId, updateTodoReq = updateTodoReq)
    }

    override suspend fun completeTodoById(
        todoId: Long
    ): Response<CommonResponse<TodoResponse>> {
        return apiService.completeTodoById(todoId = todoId)
    }
}