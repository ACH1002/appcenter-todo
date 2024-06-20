package com.example.appcenter_todolist.ui.screens.mybucket

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.appcenter_todolist.model.bucket.UpdateBucketRequest
import com.example.appcenter_todolist.navigation.AppNavigationActionsAfterLogin
import com.example.appcenter_todolist.ui.components.item.TodoItem
import com.example.appcenter_todolist.ui.components.dialog.AddTodoDialog
import com.example.appcenter_todolist.ui.theme.Background
import com.example.appcenter_todolist.ui.theme.BlackTextColor
import com.example.appcenter_todolist.ui.theme.BottomBackground
import com.example.appcenter_todolist.ui.theme.CustomTypography
import com.example.appcenter_todolist.ui.theme.GrayButtonContainer
import com.example.appcenter_todolist.ui.theme.GrayTextColor
import com.example.appcenter_todolist.ui.theme.OrangeButtonContent
import com.example.appcenter_todolist.viewmodel.BucketState
import com.example.appcenter_todolist.viewmodel.BucketViewModel
import com.example.appcenter_todolist.viewmodel.CommentViewModel
import com.example.appcenter_todolist.viewmodel.MemberViewModel
import com.example.appcenter_todolist.viewmodel.MyInfoState
import com.example.appcenter_todolist.viewmodel.TodoListState
import com.example.appcenter_todolist.viewmodel.TodoViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import es.dmoral.toasty.Toasty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBucketDetailScreen(
    appNavigationActionsAfterLogin: AppNavigationActionsAfterLogin,
    bucketViewModel: BucketViewModel,
    todoViewModel: TodoViewModel,
    commentViewModel: CommentViewModel,
    memberViewModel: MemberViewModel
) {
    val context = LocalContext.current

    val myInfo by memberViewModel.myInfoState.collectAsState()

    val selectedBucket by bucketViewModel.selectedBucketState.collectAsState()

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = BottomBackground, // 원하는 색상으로 변경
    )

    val todoListState by todoViewModel.todoListState.collectAsState()

    LaunchedEffect(Unit) {
        if (selectedBucket is BucketState.Success) {
            todoViewModel.fetchTodoList(bucketId = (selectedBucket as BucketState.Success).bucket.bucketId)
        } else if (selectedBucket is BucketState.Error) {
            Log.d("MyBucketDetailScreen", (selectedBucket as BucketState.Error).message)
        }
    }

    //투두 추가 다이얼로그
    val (addTodoDialog, setAddTodoDialog) = remember {
        mutableStateOf(false)
    }

    if (addTodoDialog) {

        AddTodoDialog(
            selectedBucket = selectedBucket,
            todoViewModel = todoViewModel,
            setAddTodoDialog = { setAddTodoDialog(it) }
        )

    }

    //버킷 내용 수정
    var bucketContent by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue((selectedBucket as BucketState.Success).bucket.content))
    }

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }


    //연속으로 뒤로가기 누를 경우에 대한 대처
    var isBackButtonPressed by remember { mutableStateOf(false) }

    BackHandler {
        if (!isBackButtonPressed) {
            isBackButtonPressed = true
            Log.d("isBack", "isBack")
            appNavigationActionsAfterLogin.popBackStack()
        }
    }

    var isReadOnly by remember {
        mutableStateOf(true)
    }

    when (selectedBucket) {
        is BucketState.Loading -> {

        }

        is BucketState.Error -> {

        }

        is BucketState.Success -> {
            Scaffold(
                floatingActionButton = {
                    Row(
                        modifier = Modifier
                            .padding(bottom = 20.dp, end = 10.dp)
                            .width(180.dp)
                            .height(36.dp)
                            .clickable {
                                setAddTodoDialog(true)
                            }
                            .background(
                                color = GrayButtonContainer,
                                shape = RoundedCornerShape(30.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "상세 투두 추가하기",
                            style = CustomTypography.bodyInterSmall16.copy(color = OrangeButtonContent),
                            modifier = Modifier
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                },
                floatingActionButtonPosition = FabPosition.End
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .background(color = Background)
                ) {

                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Background)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Background)
                        ) {
                            Row(
                                modifier = Modifier
                                    .height(135.dp)
                                    .fillMaxWidth()
                                    .background(BottomBackground),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                IconButton(onClick = {
                                    if (!isBackButtonPressed) {
                                        isBackButtonPressed = true
                                        appNavigationActionsAfterLogin.popBackStack()
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBackIosNew,
                                        contentDescription = null,
                                        tint = BlackTextColor
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .width(240.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    TextField(
                                        value = bucketContent,
                                        onValueChange = { bucketContent = it },
                                        textStyle = CustomTypography.headerInter20.copy(
                                            color = BlackTextColor.copy(alpha = 0.81f),
                                            textAlign = TextAlign.Center
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
                                                if (bucketContent.text.isNotEmpty()) {
                                                    bucketViewModel.updateBucket(
                                                        bucketId = (selectedBucket as BucketState.Success).bucket.bucketId,
                                                        updateBucketRequest = UpdateBucketRequest(
                                                            content = bucketContent.text,
                                                            deadLine = "2024-05-25"
                                                        )
                                                    )
                                                    isReadOnly = true
                                                } else {
                                                    Toasty.warning(context, "버킷의 내용을 입력해주세요", Toast.LENGTH_SHORT).show()
                                                }
                                                focusManager.clearFocus()
                                            }
                                        )
                                    )
                                    IconButton(onClick = {
                                        if (isReadOnly) {
                                            isReadOnly = false
                                            bucketContent = bucketContent.copy(selection = TextRange(bucketContent.text.length))
                                            focusRequester.requestFocus()
                                        } else {
                                            if (bucketContent.text.isNotEmpty()) {
                                                bucketViewModel.updateBucket(
                                                    bucketId = (selectedBucket as BucketState.Success).bucket.bucketId,
                                                    updateBucketRequest = UpdateBucketRequest(
                                                        content = bucketContent.text,
                                                        deadLine = "2024-05-25"
                                                    )
                                                )
                                                isReadOnly = true
                                            } else {
                                                Toasty.warning(context, "버킷의 내용을 입력해주세요", Toast.LENGTH_SHORT).show()
                                            }
                                            focusManager.clearFocus()
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
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                            ) {
                                when (todoListState) {
                                    is TodoListState.Loading -> {

                                    }

                                    is TodoListState.Error -> {

                                    }

                                    is TodoListState.Success -> {
                                        LazyColumn {
                                            item { Spacer(modifier = Modifier.height(100.dp)) }
                                            items((todoListState as TodoListState.Success).todos) { todo ->
                                                TodoItem(
                                                    selectedTodo = todo,
                                                    selectedBucket = (selectedBucket as BucketState.Success).bucket,
                                                    todoViewModel = todoViewModel,
                                                    commentViewModel = commentViewModel,
                                                    myInfo = (myInfo as MyInfoState.Success).myInfo,
                                                    isMine = (myInfo as MyInfoState.Success).myInfo.id == todo.memberId
                                                )
                                                Spacer(modifier = Modifier.height(38.dp))
                                            }
                                        }
                                    }
                                }

                            }
                        }

                    }
                }
            }
        }


    }
}