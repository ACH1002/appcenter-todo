package com.example.appcenter_todolist.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.window.Dialog
import com.example.appcenter_todolist.model.comment.AddCommentReq
import com.example.appcenter_todolist.model.comment.CommentResponse
import com.example.appcenter_todolist.model.todo.AddTodoReq
import com.example.appcenter_todolist.model.todo.TodoResponse
import com.example.appcenter_todolist.viewmodel.CommentListState
import com.example.appcenter_todolist.viewmodel.CommentViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestCommentDialog(
    selectedTodoResponse: TodoResponse,
    commentViewModel: CommentViewModel,
    onDismiss: () -> Unit
) {
    val commentListState by commentViewModel.commentListState.collectAsState()

    var commentContent by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    LaunchedEffect(Unit) {
        commentViewModel.fetchComments(selectedTodoResponse.id)
    }

    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .background(color = Color.White, shape = RoundedCornerShape(10.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "${selectedTodoResponse.content}의 댓글")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                TextField(
                    value = commentContent,
                    onValueChange = { commentContent = it },
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
                        commentViewModel.addCommentByTodo(
                            todoId = selectedTodoResponse.id,
                            addCommentReq = AddCommentReq(content = commentContent.text)
                        )
                        commentContent = TextFieldValue("")
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
            when (commentListState) {
                is CommentListState.Loading -> {
                    CircularProgressIndicator()
                }

                is CommentListState.Success -> {
                    val comments = (commentListState as CommentListState.Success).comments
                    CommentList(comments)
                }

                is CommentListState.Error -> {
                    Text(text = "Error: ${(commentListState as CommentListState.Error).message}")
                }
            }
        }
    }
}

@Composable
fun CommentList(comments: List<CommentResponse>) {
    LazyColumn {
        items(comments) { comment ->
            CommentItem(comment)
        }
    }
}

@Composable
fun CommentItem(comment: CommentResponse) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = "Author: ${comment.nickname}")
        Text(text = "Content: ${comment.content}")
    }
}