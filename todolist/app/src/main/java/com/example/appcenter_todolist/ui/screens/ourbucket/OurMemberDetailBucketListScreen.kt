package com.example.appcenter_todolist.ui.screens.ourbucket

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.appcenter_todolist.navigation.AppNavigationActions
import com.example.appcenter_todolist.ui.components.item.BucketItem
import com.example.appcenter_todolist.ui.components.toolbar.ToolBarOurBuckets
import com.example.appcenter_todolist.ui.theme.ButtonContainer
import com.example.appcenter_todolist.viewmodel.BucketListState
import com.example.appcenter_todolist.viewmodel.BucketViewModel
import com.example.appcenter_todolist.viewmodel.MemberViewModel
import com.example.appcenter_todolist.viewmodel.MyInfoState
import com.example.appcenter_todolist.viewmodel.SelectedMemberState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import es.dmoral.toasty.Toasty

@Composable
fun OurMemberDetailBucketList(
    appNavigationActions: AppNavigationActions,
    memberViewModel: MemberViewModel,
    bucketViewModel: BucketViewModel
){

    val context = LocalContext.current



    val myInfo by memberViewModel.myInfoState.collectAsState()
    val selectedMemberState by memberViewModel.selectedMemberState.collectAsState()
    val hasFetchedRandomMembers by memberViewModel.hasFetchedRandomMembers.collectAsState()


    val selectedMemberBucketListState by bucketViewModel.selectedMemberBucketListState.collectAsState()


    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = ButtonContainer, // 원하는 색상으로 변경
    )

    LaunchedEffect(Unit) {
        if (!hasFetchedRandomMembers) {
            memberViewModel.getRandomMembers()
            memberViewModel.hasFetchRandomMembers()
        }
    }

    LaunchedEffect(selectedMemberState) {
        when (selectedMemberState) {
            is SelectedMemberState.Success -> {
                bucketViewModel.getBucketListByMember(selectedMember = (selectedMemberState as SelectedMemberState.Success).selectedMember)
            }

            is SelectedMemberState.Loading -> {

            }

            is SelectedMemberState.Error -> {
                Toasty.error(
                    context,
                    (selectedMemberState as SelectedMemberState.Error).message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    ToolBarOurBuckets(
        appNavigationActions = appNavigationActions,
        bucketViewModel = bucketViewModel,
        memberViewModel = memberViewModel,
        isBack = true
    ) {
        when(selectedMemberBucketListState){
            is BucketListState.Loading->{
                CircularProgressIndicator()
            }
            is BucketListState.Error->{
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "데이터 불러오기 실패: 이유 ${(selectedMemberBucketListState as BucketListState.Error).message}")
                }
            }
            is BucketListState.Success->{
                val buckets = (selectedMemberBucketListState as BucketListState.Success).buckets
                Column {
                    Spacer(modifier = Modifier.height(35.dp))
                    LazyColumn {
                        items(buckets) { bucket ->
                            BucketItem(
                                bucketResponse = bucket,
                                bucketViewModel = bucketViewModel,
                                appNavigationActions = appNavigationActions,
                                isMine = (myInfo as MyInfoState.Success).myInfo.id == bucket.memberId,
                                onClickDetailButton = {
                                    bucketViewModel.setSelectedAnyoneBucketState(bucket = bucket)
                                    appNavigationActions.navigateToOurDetailBuckets()
                                }
                            )
                        }
                    }
                }
            }
        }

    }


}
