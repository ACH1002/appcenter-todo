package com.example.appcenter_todolist.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.appcenter_todolist.viewmodel.TodoListState
import com.example.appcenter_todolist.viewmodel.TodoViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TestScreen(
    todoViewModel: TodoViewModel = koinViewModel()
){
    val todoListState by todoViewModel.todoListState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            todoViewModel.fetchTodoList()
        }) {
            Text(text = "fetch TodoList")
        }

        when (todoListState) {
            is TodoListState.Loading -> {
                CircularProgressIndicator()
            }
            is TodoListState.Error -> {
                Text(text = (todoListState as TodoListState.Error).message, style = MaterialTheme.typography.bodyMedium, color = Color.Red)
            }
            is TodoListState.Success -> {
                val todos = (todoListState as TodoListState.Success).todos
                LazyColumn {
                    items(todos) { todo ->
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 10.dp, vertical = 10.dp)
                                .fillMaxWidth()
                                .border(width = 1.dp, color = if (todo.completed) Color.Green else Color.Red, shape = RoundedCornerShape(10.dp))
                                .padding(horizontal = 10.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(text = todo.content)
                            Text(text = if (todo.completed) "완료" else "미완료")
                        }
                    }
                }
            }
        }
    }
}