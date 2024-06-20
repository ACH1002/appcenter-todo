package com.example.appcenter_todolist.ui.screens.ourbucket

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.appcenter_todolist.navigation.AppNavigationActionsAfterLogin
import com.example.appcenter_todolist.ui.components.item.TodoItem
import com.example.appcenter_todolist.ui.components.dialog.AddTodoDialog
import com.example.appcenter_todolist.ui.theme.Background
import com.example.appcenter_todolist.ui.theme.BlackTextColor
import com.example.appcenter_todolist.ui.theme.BottomBackground
import com.example.appcenter_todolist.ui.theme.CustomTypography
import com.example.appcenter_todolist.viewmodel.BucketState
import com.example.appcenter_todolist.viewmodel.BucketViewModel
import com.example.appcenter_todolist.viewmodel.CommentViewModel
import com.example.appcenter_todolist.viewmodel.MemberViewModel
import com.example.appcenter_todolist.viewmodel.MyInfoState
import com.example.appcenter_todolist.viewmodel.TodoListState
import com.example.appcenter_todolist.viewmodel.TodoViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OurBucketDetailScreen(
    appNavigationActionsAfterLogin: AppNavigationActionsAfterLogin,
    bucketViewModel: BucketViewModel,
    todoViewModel: TodoViewModel,
    commentViewModel: CommentViewModel,
    memberViewModel: MemberViewModel
){
    val myInfo by memberViewModel.myInfoState.collectAsState()

    val selectedAnyoneBucketState by bucketViewModel.selectedAnyoneBucketState.collectAsState()

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = BottomBackground, // 원하는 색상으로 변경
    )

    val todoListState by todoViewModel.todoListState.collectAsState()

    LaunchedEffect(Unit){
        if (selectedAnyoneBucketState is BucketState.Success) {
            todoViewModel.fetchTodoList(bucketId = (selectedAnyoneBucketState as BucketState.Success).bucket.bucketId)
        } else if (selectedAnyoneBucketState is BucketState.Error){
            Log.d("MyBucketDetailScreen", (selectedAnyoneBucketState as BucketState.Error).message)
        }
    }

    val (addTodoDialog, setAddTodoDialog) = remember {
        mutableStateOf(false)
    }

    if (addTodoDialog) {

        AddTodoDialog(
            selectedBucket = selectedAnyoneBucketState,
            todoViewModel = todoViewModel,
            setAddTodoDialog = {setAddTodoDialog(it)}
        )

    }

    var isBackButtonPressed by remember { mutableStateOf(false) }

    BackHandler {
        if (!isBackButtonPressed) {
            isBackButtonPressed = true
            Log.d("isBack", "isBack")
            appNavigationActionsAfterLogin.popBackStack()
        }
    }

    when(selectedAnyoneBucketState){
        is BucketState.Loading -> {

        }
        is BucketState.Error -> {

        }
        is BucketState.Success -> {
            Scaffold(
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
                                }
                                ) {
                                    Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = null, tint = BlackTextColor)
                                }
                                Text(
                                    text = (selectedAnyoneBucketState as BucketState.Success).bucket.content,
                                    style = CustomTypography.headerInter20.copy(color = BlackTextColor.copy(alpha = 0.81f)),
                                    modifier = Modifier
                                        .width(240.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                            ) {
                                when(todoListState){
                                    is TodoListState.Loading->{

                                    }
                                    is TodoListState.Error->{

                                    }
                                    is TodoListState.Success->{
                                        LazyColumn{
                                            item { Spacer(modifier = Modifier.height(100.dp)) }
                                            items((todoListState as TodoListState.Success).todos){todo ->
                                                TodoItem(
                                                    selectedTodo = todo,
                                                    selectedBucket = (selectedAnyoneBucketState as BucketState.Success).bucket,
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