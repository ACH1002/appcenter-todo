package com.example.appcenter_todolist.ui.screens.ourbucket

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcenter_todolist.navigation.AppNavigationActionsAfterLogin
import com.example.appcenter_todolist.ui.components.item.BucketItem
import com.example.appcenter_todolist.ui.components.toolbar.ToolBarOurBuckets
import com.example.appcenter_todolist.ui.theme.Background
import com.example.appcenter_todolist.ui.theme.BlackTextColor
import com.example.appcenter_todolist.ui.theme.ButtonContainer
import com.example.appcenter_todolist.ui.theme.CustomTypography
import com.example.appcenter_todolist.ui.theme.GrayDividerColor
import com.example.appcenter_todolist.ui.theme.OrangeButtonContainer
import com.example.appcenter_todolist.ui.theme.OrangeButtonContent
import com.example.appcenter_todolist.ui.theme.SelectedOurBucketRandomMemberContainer
import com.example.appcenter_todolist.ui.theme.SelectedOurBucketRandomMemberContent
import com.example.appcenter_todolist.ui.theme.UnselectedOurBucketRandomMemberContainer
import com.example.appcenter_todolist.ui.theme.UnselectedOurBucketRandomMemberContent
import com.example.appcenter_todolist.viewmodel.BucketListState
import com.example.appcenter_todolist.viewmodel.BucketViewModel
import com.example.appcenter_todolist.viewmodel.MemberViewModel
import com.example.appcenter_todolist.viewmodel.MyInfoState
import com.example.appcenter_todolist.viewmodel.RandomMembersState
import com.example.appcenter_todolist.viewmodel.SelectedMemberState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import es.dmoral.toasty.Toasty

@Composable
fun OurBucketsScreen(
    appNavigationActionsAfterLogin: AppNavigationActionsAfterLogin,
    memberViewModel: MemberViewModel,
    bucketViewModel: BucketViewModel
) {
    val context = LocalContext.current

    val myInfo by memberViewModel.myInfoState.collectAsState()
    val randomMembersState by memberViewModel.randomMembersState.collectAsState()
    val selectedMemberState by memberViewModel.selectedMemberState.collectAsState()
    val hasFetchedRandomMembers by memberViewModel.hasFetchedRandomMembers.collectAsState()
    val searchedNickname by memberViewModel.searchedNickname.collectAsState()


    val selectedMemberBucketListState by bucketViewModel.selectedMemberBucketListState.collectAsState()
    val searchedMemberBucketListState by bucketViewModel.searchedMemberBucketListState.collectAsState()


    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = ButtonContainer, // 원하는 색상으로 변경
    )

    val scrollState = rememberScrollState()

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
        appNavigationActionsAfterLogin = appNavigationActionsAfterLogin,
        bucketViewModel = bucketViewModel,
        memberViewModel = memberViewModel
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)

        ) {
            when(searchedMemberBucketListState){
                is BucketListState.Loading->{
                    Row(
                        modifier = Modifier
                            .background(ButtonContainer)
                            .padding(start = 20.dp, end = 20.dp, bottom = 35.dp)
                            .fillMaxWidth()
                            .height(150.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(9.dp)
                    ) {
                        when (randomMembersState) {
                            is RandomMembersState.Loading -> {
                                CircularProgressIndicator()
                            }

                            is RandomMembersState.Error -> {
                                Text(
                                    text = (randomMembersState as RandomMembersState.Error).message,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Red
                                )
                            }

                            is RandomMembersState.Success -> {
                                val randomMembers =
                                    (randomMembersState as RandomMembersState.Success).randomMembers
                                val numberOfSpacers = 3 - randomMembers.size

                                randomMembers.forEach { randomMember ->
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxHeight()
                                            .background(
                                                color = if ((selectedMemberState as SelectedMemberState.Success).selectedMember.id == randomMember.id) {
                                                    SelectedOurBucketRandomMemberContainer
                                                } else {
                                                    UnselectedOurBucketRandomMemberContainer
                                                },
                                                shape = RoundedCornerShape(12.dp)
                                            ),
                                    ) {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Column {
                                                Box(
                                                    modifier = Modifier
                                                        .padding(10.dp)
                                                        .size(80.dp)
                                                        .clip(CircleShape)
                                                        .background(Background)
                                                        .border(1.dp, GrayDividerColor, CircleShape)
                                                        .clickable {
                                                            memberViewModel.setSelectedMember(
                                                                randomMember
                                                            )
                                                        }
                                                )
                                                Text(
                                                    text = randomMember.nickname,
                                                    style = CustomTypography.bodyInter20.copy(
                                                        color = Color(
                                                            0xFF666666
                                                        ), fontSize = 13.sp
                                                    ),
                                                    modifier = Modifier.fillMaxWidth(),
                                                    textAlign = TextAlign.Center
                                                )
                                            }
                                            Button(
                                                onClick = {
                                                    memberViewModel.setSelectedMember(
                                                        randomMember
                                                    )
                                                    appNavigationActionsAfterLogin.navigateToOurMemberDetailBuckets()
                                                },
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = if ((selectedMemberState as SelectedMemberState.Success).selectedMember.id == randomMember.id) {
                                                        SelectedOurBucketRandomMemberContent
                                                    } else {
                                                        UnselectedOurBucketRandomMemberContent
                                                    },
                                                    contentColor = Background
                                                ),
                                                modifier = Modifier
                                                    .shadow(4.dp, RoundedCornerShape(20.dp))
                                                    .width(70.dp)
                                                    .height(25.dp),
                                                contentPadding = PaddingValues(0.dp),
                                                shape = RoundedCornerShape(20.dp)
                                            ) {
                                                Text(
                                                    text = "상세 버킷",
                                                    style = if ((selectedMemberState as SelectedMemberState.Success).selectedMember.id == randomMember.id) {
                                                        CustomTypography.bodyInter20.copy(
                                                            fontSize = 11.sp,
                                                            color = OrangeButtonContent
                                                        )
                                                    } else {
                                                        CustomTypography.textFieldInter15.copy(
                                                            fontSize = 11.sp,
                                                            color = OrangeButtonContent
                                                        )
                                                    }
                                                )
                                            }
                                        }

                                    }
                                }

                                repeat(numberOfSpacers) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                    Column(
                    ) {
                        when (selectedMemberBucketListState) {
                            is BucketListState.Loading -> {

                            }

                            is BucketListState.Error -> {

                            }

                            is BucketListState.Success -> {
                                LazyColumn {
                                    item {
                                        Text(
                                            text = buildAnnotatedString {
                                                withStyle(style = SpanStyle(color = Color(0xFFd67419))) {
                                                    append((selectedMemberState as SelectedMemberState.Success).selectedMember.nickname)
                                                }
                                                append("님의 버킷 투두리스트")
                                            },
                                            style = CustomTypography.headerInter20.copy(color = BlackTextColor),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier
                                                .padding(horizontal = 15.dp, vertical = 35.dp)
                                                .fillMaxWidth()
                                        )
                                    }

                                    items((selectedMemberBucketListState as BucketListState.Success).buckets) { bucket ->
                                        BucketItem(
                                            bucketResponse = bucket,
                                            bucketViewModel = bucketViewModel,
                                            appNavigationActionsAfterLogin = appNavigationActionsAfterLogin,
                                            isMine = (myInfo as MyInfoState.Success).myInfo.id == bucket.memberId,
                                            onClickDetailButton = {
                                                bucketViewModel.setSelectedAnyoneBucketState(bucket = bucket)
                                                appNavigationActionsAfterLogin.navigateToOurDetailBuckets()
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                is BucketListState.Error->{
                    Log.d("no search member", "검색된 멤버 없음")
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "검색 결과가 없습니다", style = CustomTypography.headerInter20.copy(color = OrangeButtonContainer))
                    }
                }

                is BucketListState.Success -> {
                    val buckets = (searchedMemberBucketListState as BucketListState.Success).buckets
                    Log.d("searchedMemberBucketListState", buckets.toString())
                    Column {
                        LazyColumn {
                            item {
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(style = SpanStyle(color = Color(0xFFd67419))) {
                                            append(searchedNickname)
                                        }
                                        append("님의 버킷 투두리스트")
                                    },
                                    style = CustomTypography.headerInter20.copy(color = BlackTextColor),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                        .padding(horizontal = 15.dp, vertical = 35.dp)
                                        .fillMaxWidth()
                                )
                            }
                            items(buckets) { bucket ->
                                BucketItem(
                                    bucketResponse = bucket,
                                    bucketViewModel = bucketViewModel,
                                    appNavigationActionsAfterLogin = appNavigationActionsAfterLogin,
                                    isMine = (myInfo as MyInfoState.Success).myInfo.id == bucket.memberId,
                                    onClickDetailButton = {
                                        bucketViewModel.setSelectedAnyoneBucketState(bucket = bucket)
                                        appNavigationActionsAfterLogin.navigateToOurDetailBuckets()
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