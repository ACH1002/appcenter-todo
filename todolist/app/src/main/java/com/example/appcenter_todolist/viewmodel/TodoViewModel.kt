package com.example.appcenter_todolist.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcenter_todolist.model.todo.AddTodoReq
import com.example.appcenter_todolist.model.todo.TodoResponse
import com.example.appcenter_todolist.model.todo.UpdateTodoReq
import com.example.appcenter_todolist.network.ApiException
import com.example.appcenter_todolist.repository.TodoRepository
import com.example.appcenter_todolist.repository.TokenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class TodoListState {
    object Loading : TodoListState()
    data class Success(val todos: List<TodoResponse>) : TodoListState()
    data class Error(val message: String) : TodoListState()
}

sealed class TodoState {
    object Loading : TodoState()
    data class Success(val todos: TodoResponse) : TodoState()
    data class Error(val message: String) : TodoState()
}

class TodoViewModel(private val todoRepository: TodoRepository, private val tokenRepository: TokenRepository): ViewModel() {

    private val _todoListState: MutableStateFlow<TodoListState> = MutableStateFlow(TodoListState.Loading)
    val todoListState = _todoListState.asStateFlow()

    private val _todoState: MutableStateFlow<TodoState> = MutableStateFlow(TodoState.Loading)
    val todoState = _todoState.asStateFlow()


    init {
//        fetchTodoList()
    }
    private suspend fun getMemberId(): Long {
        return tokenRepository.getMember() ?: throw Exception("Member ID가 저장되어 있지 않습니다.")
    }
    fun fetchTodoList() {
        viewModelScope.launch {
            try {
                val memberId = getMemberId()
                val response = todoRepository.getTodos(memberId = memberId)
                if (response.isSuccessful){
                    val newTodoList = response.body() ?: throw Exception("TodoList 정보가 비어있습니다.")
                    _todoListState.update { TodoListState.Success(newTodoList.response) }
                } else {
                    throw Exception(response.errorBody()?.string())
                }
            } catch (e: ApiException) {
                Log.e("투두 리스트 가져오기 실패 원인", e.errorResponse.message)
                _todoListState.update { TodoListState.Error(e.errorResponse.message) }
            } catch (e: Exception) {
                Log.e("투두 리스트 가져오기 실패 원인", e.message.toString())
                _todoListState.update { TodoListState.Error(e.message.toString()) }
            } catch (e: RuntimeException){
                Log.e("투두 리스트 가져오기 시간초과", e.message.toString())
                _todoListState.update { TodoListState.Error(e.message.toString()) }
            }
        }
    }

    fun addTodo(addTodoReq: AddTodoReq) {
        viewModelScope.launch {
            try {
                val memberId = getMemberId()
                val response = todoRepository.addTodo(memberId = memberId, addTodoReq = addTodoReq)
                if (response.isSuccessful) {
                    val newTodo = response.body() ?: throw Exception("추가한 Todo의 정보가 비어있습니다.")
                    Log.d("addTodo 성공", "새로운 Todo ID: ${newTodo.response}")
                } else {
                    throw Exception(response.errorBody()?.string())
                }
            } catch (e: ApiException) {
                Log.e("투두 추가 실패 원인", e.errorResponse.message)
                _todoListState.update { TodoListState.Error(e.errorResponse.message) }
            } catch (e: Exception) {
                Log.e("투두 추가 실패 원인", e.message.toString())
                _todoListState.update { TodoListState.Error(e.message.toString()) }
            } catch (e: RuntimeException) {
                Log.e("투두 추가 시간초과", e.message.toString())
                _todoListState.update { TodoListState.Error(e.message.toString()) }
            }
        }
    }


    fun deleteTodoById(
        todoId : Long
    ){
        viewModelScope.launch {
            try {
                val response = todoRepository.deleteTodoById(todoId = todoId)
                if (response.isSuccessful){
                    val deletedTodo = response.body() ?: throw Exception("삭제한 Todo의 정보가 비어있습니다.")
                    Log.d("deleteTodo 성공", "삭제된 Todo ID: ${deletedTodo.response}")
                    fetchTodoList()
                } else {
                    throw Exception(response.errorBody()?.string())
                }
            } catch (e: ApiException) {
                Log.e("투두 삭제 실패 원인", e.errorResponse.message)
                _todoListState.update { TodoListState.Error(e.errorResponse.message) }
            } catch (e: Exception) {
                Log.e("투두 삭제 실패 원인", e.message.toString())
                _todoListState.update { TodoListState.Error(e.message.toString()) }
            } catch (e: RuntimeException){
                Log.e("투두 삭제 시간초과", e.message.toString())
                _todoListState.update { TodoListState.Error(e.message.toString()) }
            }
        }
    }

    fun updateTodoById(
        todoId: Long,
        updateTodoReq: UpdateTodoReq
    ){
        viewModelScope.launch {
            try {
                val response = todoRepository.updateTodoById(todoId = todoId, updateTodoReq = updateTodoReq)
                if (response.isSuccessful){
                    val newTodo = response.body() ?: throw Exception("수정한 Todo의 정보가 비어있습니다.")
                    Log.d("updateTodo 성공", "수정한 Todo ID: ${newTodo.response}")
                    fetchTodoList()
                } else {
                    throw Exception(response.errorBody()?.string())
                }
            } catch (e: ApiException) {
                Log.e("투두 수정 실패 원인", e.errorResponse.message)
                _todoListState.update { TodoListState.Error(e.errorResponse.message) }
            } catch (e: Exception) {
                Log.e("투두 수정 실패 원인", e.message.toString())
                _todoListState.update { TodoListState.Error(e.message.toString()) }
            } catch (e: RuntimeException){
                Log.e("투두 수정 시간초과", e.message.toString())
                _todoListState.update { TodoListState.Error(e.message.toString()) }
            }
        }
    }

    fun completeTodoById(
        todoId: Long
    ){
        viewModelScope.launch {
            try {
                val response = todoRepository.completeTodoById( todoId = todoId)
                if (response.isSuccessful){
                    val newTodo = response.body() ?: throw Exception("삭제한 Todo의 정보가 비어있습니다.")
                    Log.d("deleteTodo 성공", "삭제한 Todo ID: ${newTodo.response}")
                    fetchTodoList()
                } else {
                    throw Exception(response.errorBody()?.string())
                }
            } catch (e: ApiException) {
                Log.e("투두 삭제 실패 원인", e.errorResponse.message)
                _todoListState.update { TodoListState.Error(e.errorResponse.message) }
            } catch (e: Exception) {
                Log.e("투두 삭제 실패 원인", e.message.toString())
                _todoListState.update { TodoListState.Error(e.message.toString()) }
            } catch (e: RuntimeException){
                Log.e("투두 삭제 시간초과", e.message.toString())
                _todoListState.update { TodoListState.Error(e.message.toString()) }
            }
        }
    }
}