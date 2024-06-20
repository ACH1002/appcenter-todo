package com.example.appcenter_todolist.ui.components.dialog

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.appcenter_todolist.R
import com.example.appcenter_todolist.model.bucket.BucketResponse
import com.example.appcenter_todolist.model.comment.AddCommentReq
import com.example.appcenter_todolist.model.member.MemberResponse
import com.example.appcenter_todolist.model.todo.TodoResponse
import com.example.appcenter_todolist.model.todo.UpdateTodoReq
import com.example.appcenter_todolist.ui.components.item.CommentItem
import com.example.appcenter_todolist.ui.components.textfield.CommentTextField
import com.example.appcenter_todolist.ui.theme.Background
import com.example.appcenter_todolist.ui.theme.BlackTextColor
import com.example.appcenter_todolist.ui.theme.CustomTypography
import com.example.appcenter_todolist.ui.theme.Dimensions
import com.example.appcenter_todolist.ui.theme.GrayDividerColor
import com.example.appcenter_todolist.ui.theme.GrayTextColor
import com.example.appcenter_todolist.viewmodel.CommentListState
import com.example.appcenter_todolist.viewmodel.CommentViewModel
import com.example.appcenter_todolist.viewmodel.TodoViewModel
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.launch

@SuppressLint("CheckResult")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCommentDialog(
    selectedBucket: BucketResponse,
    selectedTodo: TodoResponse,
    setAddCommentDialog: (Boolean) -> Unit,
    commentViewModel: CommentViewModel,
    todoViewModel: TodoViewModel,
    myInfo: MemberResponse,
    isMine: Boolean
) {
    val context = LocalContext.current

    val commentListState by commentViewModel.commentListState.collectAsState()
    val addCommentState by commentViewModel.addCommentState.collectAsState()

    var addCommentContent by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    LaunchedEffect(Unit) {
        commentViewModel.fetchComments(todoId = selectedTodo.id)
    }

    //댓글 추가 시 맨 아래로 스크롤
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(commentListState) {
        if (commentListState is CommentListState.Success) {
            coroutineScope.launch {
                listState.animateScrollToItem((commentListState as CommentListState.Success).comments.size)
            }
        }
    }

    //버킷 내용 수정
    var todoContent by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(selectedTodo.content))
    }

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    var isReadOnly by remember {
        mutableStateOf(true)
    }

    Dialog(
        onDismissRequest = { setAddCommentDialog(false) },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            modifier = Modifier
                .padding(Dimensions.SidePadding)
                .width(380.dp)
                .height(430.dp),
            shape = RoundedCornerShape(30.dp),
            color = Background
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {},
                        actions = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                IconButton(onClick = { setAddCommentDialog(false) }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = null,
                                        tint = BlackTextColor
                                    )
                                }
                            }

                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Background
                        )
                    )
                },
                containerColor = Background,
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(horizontal = Dimensions.SidePadding)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            if (isMine) {
                                todoViewModel.completeTodoById(
                                    bucketId = selectedBucket.bucketId,
                                    todoId = selectedTodo.id
                                )
                            }
                        }) {
                            Icon(
                                painter = painterResource(id = if (selectedTodo.completed) R.drawable.checked_box else R.drawable.unchecked_box),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(27.dp),
                                tint = BlackTextColor
                            )
                        }
                        Row(
                            modifier = Modifier
                                .weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextField(
                                value = todoContent,
                                onValueChange = { todoContent = it },
                                textStyle = CustomTypography.bodyInter20.copy(
                                    color = BlackTextColor.copy(
                                        alpha = 0.81f
                                    ), fontSize = 32.sp, lineHeight = 48.sp
                                ),
                                readOnly = isReadOnly,
                                colors = TextFieldDefaults.textFieldColors(
                                    containerColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .focusRequester(focusRequester = focusRequester),
                                maxLines = 3,
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        if (todoContent.text.isNotEmpty()) {
                                            todoViewModel.updateTodoById(
                                                bucketId = selectedBucket.bucketId,
                                                todoId = selectedTodo.id,
                                                updateTodoReq = UpdateTodoReq(
                                                    content = todoContent.text,
                                                    deadLine = "2024-05-25"
                                                ),

                                            )
                                            isReadOnly = true
                                            focusManager.clearFocus()
                                        } else {
                                            Toasty.warning(context, "투두 내용을 입력해주세요", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                )
                            )
                            if (isMine){
                                IconButton(onClick = {
                                    if (isReadOnly) {
                                        isReadOnly = false
                                        todoContent = todoContent.copy(selection = TextRange(todoContent.text.length))
                                        focusRequester.requestFocus()
                                    } else {
                                        if (todoContent.text.isNotEmpty()) {
                                            todoViewModel.updateTodoById(
                                                bucketId = selectedBucket.bucketId,
                                                todoId = selectedTodo.id,
                                                updateTodoReq = UpdateTodoReq(
                                                    content = todoContent.text,
                                                    deadLine = "2024-05-25"
                                                ),

                                                )
                                            isReadOnly = true
                                            focusManager.clearFocus()
                                        } else {
                                            Toasty.warning(context, "투두 내용을 입력해주세요", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = null,
                                        tint = if (isReadOnly) GrayTextColor else BlackTextColor
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Divider(color = GrayDividerColor.copy(alpha = 0.54f), thickness = 1.5.dp)
                    Spacer(modifier = Modifier.height(15.dp))
                    when (commentListState) {
                        is CommentListState.Loading -> {
                            CircularProgressIndicator()
                        }

                        is CommentListState.Error -> {
                            Text(
                                text = (commentListState as CommentListState.Error).message,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Red
                            )
                        }

                        is CommentListState.Success -> {
                            Column(
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                ) {
                                    Text(
                                        text = "댓글 ${(commentListState as CommentListState.Success).comments.size}",
                                        style = CustomTypography.bodyInter20.copy(
                                            color = BlackTextColor,
                                            fontSize = 15.sp
                                        ),
                                        modifier = Modifier
                                            .padding(start = 10.dp)
                                    )
                                    LazyColumn(state = listState) {
                                        if ((commentListState as CommentListState.Success).comments.isEmpty()) {
                                            item {
                                                Text(
                                                    text = "아직 댓글이 없어요",
                                                    style = CustomTypography.textFieldInter15.copy(
                                                        color = GrayTextColor
                                                    ),
                                                    modifier = Modifier
                                                        .fillMaxWidth(),
                                                    textAlign = TextAlign.Center
                                                )
                                            }
                                        } else {
                                            items((commentListState as CommentListState.Success).comments) { comment ->
                                                CommentItem(
                                                    selectedComment = comment,
                                                    myInfo = myInfo,
                                                    selectedTodo = selectedTodo,
                                                    commentViewModel = commentViewModel
                                                )
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(15.dp))
                                    Divider(
                                        color = GrayDividerColor.copy(alpha = 0.54f),
                                        thickness = 1.5.dp
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    CommentTextField(
                                        placeholder = "댓글을 입력해주세요",
                                        content = addCommentContent,
                                        updateContent = { addCommentContent = it },
                                        height = 30.dp,
                                        modifier = Modifier.weight(1f)
                                    )
                                    IconButton(onClick = {
                                        if (addCommentContent.text.isNotEmpty()) {
                                            commentViewModel.addCommentByTodo(
                                                todoId = selectedTodo.id,
                                                addCommentReq = AddCommentReq(content = addCommentContent.text)
                                            )
                                        }
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Send,
                                            contentDescription = null,
                                            tint = if (addCommentContent.text.isEmpty()) GrayTextColor else BlackTextColor
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }
                }
            }

        }


    }

    LaunchedEffect(addCommentState) {
        if (addCommentState == true) {
            addCommentContent = TextFieldValue("")
            commentViewModel.clearAddTodoState()
        } else if (addCommentState == false) {
            addCommentContent = TextFieldValue("")
            commentViewModel.clearAddTodoState()
        }
    }
}