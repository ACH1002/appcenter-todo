package com.example.appcenter_todolist.ui.screens.mybucket

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.appcenter_todolist.navigation.AppNavigationActions
import com.example.appcenter_todolist.ui.components.item.BucketItem
import com.example.appcenter_todolist.ui.components.toolbar.ToolBarAfterLogin
import com.example.appcenter_todolist.ui.theme.ButtonContainer
import com.example.appcenter_todolist.viewmodel.BucketListState
import com.example.appcenter_todolist.viewmodel.BucketViewModel
import com.example.appcenter_todolist.viewmodel.MemberViewModel
import com.example.appcenter_todolist.viewmodel.MyInfoState
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MyBucketsScreen(
    appNavigationActions: AppNavigationActions,
    bucketViewModel: BucketViewModel,
    memberViewModel: MemberViewModel
) {
    val systemUiController = rememberSystemUiController()


    val myInfoState by memberViewModel.myInfoState.collectAsState()

    val myBucketListState by bucketViewModel.bucketListState.collectAsState()

    systemUiController.setSystemBarsColor(
        color = ButtonContainer, // 원하는 색상으로 변경
    )

    LaunchedEffect(Unit) {
        memberViewModel.getMyInfo()
        bucketViewModel.fetchBucketList()
    }


    when (myInfoState) {
        is MyInfoState.Loading -> {
            CircularProgressIndicator()
        }

        is MyInfoState.Error -> {
            Text(
                text = (myInfoState as MyInfoState.Error).message,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Red
            )
        }

        is MyInfoState.Success -> {
            ToolBarAfterLogin(
                appNavigationActions = appNavigationActions,
                myInfoState = myInfoState as MyInfoState.Success,
                isFloatingButton = true,
                bucketViewModel = bucketViewModel,
                isLogout = true,
                memberViewModel = memberViewModel
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        LazyColumn {
                            when (myBucketListState) {
                                is BucketListState.Loading -> {
                                    item { CircularProgressIndicator() }
                                }

                                is BucketListState.Error -> {
                                    item {
                                        Text(
                                            text = (myBucketListState as BucketListState.Error).message,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color.Red
                                        )
                                    }
                                }

                                is BucketListState.Success -> {
                                    item {
                                        Spacer(modifier = Modifier.height(40.dp))
                                    }
                                    items(
                                        (myBucketListState as BucketListState.Success).buckets
                                    ) {bucket ->
                                        BucketItem(
                                            bucketResponse = bucket,
                                            bucketViewModel = bucketViewModel,
                                            appNavigationActions = appNavigationActions,
                                            onClickDetailButton = {
                                                bucketViewModel.setSelectedBucketState(bucket = bucket)
                                                appNavigationActions.navigateToMyDetailBucket()
                                            }
                                        )
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