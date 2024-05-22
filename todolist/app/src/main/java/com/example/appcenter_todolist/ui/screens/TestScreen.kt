package com.example.appcenter_todolist.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcenter_todolist.model.AddTodoReq
import com.example.appcenter_todolist.viewmodel.TodoListState
import com.example.appcenter_todolist.viewmodel.TodoViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestScreen(
    todoViewModel: TodoViewModel = koinViewModel()
) {
    val todoListState by todoViewModel.todoListState.collectAsState()
    var todoContent by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TextField(
                value = todoContent,
                onValueChange = { todoContent = it },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(20.dp)),
                maxLines = 1,
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                textStyle = TextStyle(
                    color = Color.Black,
                    fontFamily = FontFamily.SansSerif
                ),

            )
            Button(
                onClick = {
                    todoViewModel.addTodo(
                        addTodoReq = AddTodoReq(
                            email = "ach@naver.com",
                            content = todoContent.text
                        )
                    )
                    todoContent = TextFieldValue("")
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                border = BorderStroke(width = 1.dp, color = Color.Black),
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxHeight()
            ) {
                Text(
                    text = "추가",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }
        }


        Button(
            onClick = {
                todoViewModel.fetchTodoList()
            },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            border = BorderStroke(width = 1.dp, color = Color.Black)
        ) {
            Text(
                text = "fetch TodoList",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }

        when (todoListState) {
            is TodoListState.Loading -> {
                CircularProgressIndicator()
            }

            is TodoListState.Error -> {
                Text(
                    text = (todoListState as TodoListState.Error).message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Red
                )
            }

            is TodoListState.Success -> {
                val todos = (todoListState as TodoListState.Success).todos
                LazyColumn {
                    items(todos) { todo ->
                        Row(
                            modifier = Modifier
                                .padding(vertical = 10.dp)
                                .fillMaxWidth()
                                .border(
                                    width = 1.dp,
                                    color = if (todo.completed) Color.Green else Color.Red,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(horizontal = 10.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = todo.completed,
                                    onCheckedChange = {
                                        todoViewModel.completeTodoById(
                                            memberId = todo.id,
                                            todoId = todo.id
                                        )
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = Color.Green,
                                        checkmarkColor = Color.Green,
                                        uncheckedColor = Color.Red
                                    )
                                )
                                Text(text = todo.content)
                            }
                            Button(
                                onClick = {
                                    todoViewModel.deleteTodoById(
                                        memberId = todo.id,
                                        todoId = todo.id
                                    )
                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent
                                ),
                                border = BorderStroke(width = 1.dp, color = Color.Red)
                            ) {
                                Text(
                                    text = "삭제",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Red
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}