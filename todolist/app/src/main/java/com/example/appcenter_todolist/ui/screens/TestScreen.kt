package com.example.appcenter_todolist.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.appcenter_todolist.model.member.LoginMemberReq
import com.example.appcenter_todolist.model.member.SignupMemberReq
import com.example.appcenter_todolist.model.todo.AddTodoReq
import com.example.appcenter_todolist.viewmodel.CommentViewModel
import com.example.appcenter_todolist.viewmodel.MemberViewModel
import com.example.appcenter_todolist.viewmodel.TodoListState
import com.example.appcenter_todolist.viewmodel.TodoState
import com.example.appcenter_todolist.viewmodel.TodoViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestScreen(
    todoViewModel: TodoViewModel = koinViewModel(),
    memberViewModel: MemberViewModel = koinViewModel(),
    commentViewModel: CommentViewModel = koinViewModel()
) {
    val todoListState by todoViewModel.todoListState.collectAsState()
    val selectedTodoState by todoViewModel.selectedTodoState.collectAsState()


    var signupEmail by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    var signupPassword by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    var signupNickname by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    var loginEmail by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    var loginPassword by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    var todoContent by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    var loginShowPassword by remember { mutableStateOf(value = false) }

    var signupShowPassword by remember { mutableStateOf(value = false) }

    val (testCommentDialog, setTestCommentDialog) = remember {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()

    if (testCommentDialog) {
        TestCommentDialog(
            selectedTodoResponse = (selectedTodoState as TodoState.Success).todo,
            commentViewModel = commentViewModel,
            onDismiss = { setTestCommentDialog(false) })
    }

    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextField(
                value = signupNickname,
                onValueChange = { signupNickname = it },
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
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
                label = { Text(text = "nickname") }
            )
            TextField(
                value = signupEmail,
                onValueChange = { signupEmail = it },
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
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
                label = { Text(text = "email") }

            )
            TextField(
                value = signupPassword,
                onValueChange = { signupPassword = it },
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
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
                visualTransformation = if (signupShowPassword) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    if (signupShowPassword) {
                        IconButton(onClick = { signupShowPassword = false }) {
                            Icon(
                                imageVector = Icons.Filled.Visibility,
                                contentDescription = "hide_password"
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { signupShowPassword = true }) {
                            Icon(
                                imageVector = Icons.Filled.VisibilityOff,
                                contentDescription = "hide_password"
                            )
                        }
                    }
                },
                label = { Text(text = "password") }
            )
            Button(
                onClick = {
                    memberViewModel.signup(
                        signupMemberReq = SignupMemberReq(
                            email = signupEmail.text,
                            nickname = signupNickname.text,
                            password = signupPassword.text
                        )
                    )
                    signupEmail = TextFieldValue("")
                    signupNickname = TextFieldValue("")
                    signupPassword = TextFieldValue("")
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                border = BorderStroke(width = 1.dp, color = Color.Black),
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .height(50.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "회원가입",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            TextField(
                value = loginEmail,
                onValueChange = { loginEmail = it },
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
                label = { Text(text = "email") }

            )
            TextField(
                value = loginPassword,
                onValueChange = { loginPassword = it },
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
                visualTransformation = if (loginShowPassword) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    if (loginShowPassword) {
                        IconButton(onClick = { loginShowPassword = false }) {
                            Icon(
                                imageVector = Icons.Filled.Visibility,
                                contentDescription = "hide_password"
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { loginShowPassword = true }) {
                            Icon(
                                imageVector = Icons.Filled.VisibilityOff,
                                contentDescription = "hide_password"
                            )
                        }
                    }
                },
                label = { Text(text = "password") }
            )
            Button(
                onClick = {
                    memberViewModel.login(
                        loginMemberReq = LoginMemberReq(
                            email = loginEmail.text,
                            password = loginPassword.text
                        )
                    )
                    loginEmail = TextFieldValue("")
                    loginPassword = TextFieldValue("")
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
                    text = "로그인",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }
        }


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
//                    todoViewModel.addTodo(
//                        addTodoReq = AddTodoReq(
//                            content = todoContent.text,
//                            deadLine = LocalDate.of(2024, 5, 31)
//                        )
//                    )
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
//                todoViewModel.fetchTodoList()
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
//                                        todoViewModel.completeTodoById(
//                                            todoId = todo.id
//                                        )
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = Color.Green,
                                        checkmarkColor = Color.Green,
                                        uncheckedColor = Color.Red
                                    )
                                )
                                Text(
                                    text = todo.content,
                                    modifier = Modifier
                                        .clickable {
                                            coroutineScope.launch {
                                                todoViewModel.setSelectedTodoState(todo = todo)
                                                setTestCommentDialog(true)

                                            }
                                        }
                                )
                            }
                            Button(
                                onClick = {
//                                    todoViewModel.deleteTodoById(
//                                        todoId = todo.id
//                                    )
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