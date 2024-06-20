package com.example.appcenter_todolist.ui.components.toolbar

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.appcenter_todolist.model.bucket.AddBucketRequest
import com.example.appcenter_todolist.navigation.AllDestination
import com.example.appcenter_todolist.navigation.AppNavigationActionsAfterLogin
import com.example.appcenter_todolist.ui.components.dialog.AddBucketDialog
import com.example.appcenter_todolist.ui.theme.Background
import com.example.appcenter_todolist.ui.theme.BlackTextColor
import com.example.appcenter_todolist.ui.theme.BottomBackground
import com.example.appcenter_todolist.ui.theme.ButtonContainer
import com.example.appcenter_todolist.ui.theme.CustomTypography
import com.example.appcenter_todolist.ui.theme.GrayTextColor
import com.example.appcenter_todolist.ui.theme.OrangeButtonContent
import com.example.appcenter_todolist.ui.theme.OrangeButtonContainer
import com.example.appcenter_todolist.viewmodel.BucketViewModel
import com.example.appcenter_todolist.viewmodel.MemberViewModel
import com.example.appcenter_todolist.viewmodel.MyInfoState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolBarAfterLogin(
    appNavigationActionsAfterLogin: AppNavigationActionsAfterLogin,
    myInfoState: MyInfoState.Success,
    bucketViewModel: BucketViewModel,
    isFloatingButton: Boolean = false,
    isLogout: Boolean = false,
    memberViewModel: MemberViewModel,
    content: @Composable (PaddingValues) -> Unit,
) {
    val currentScreen = appNavigationActionsAfterLogin.getCurrentScreen()

    val (addBucketDialog, setAddBucketDialog) = remember {
        mutableStateOf(false)
    }



    if (addBucketDialog) {
        AddBucketDialog(
            setAddBucketDialog = { setAddBucketDialog(it) },
            bucketViewModel = bucketViewModel
        )
    }



    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "${myInfoState.myInfo.nickname}님의 버킷 투두리스트",
                        style = CustomTypography.headerInter20.copy(
                            color = BlackTextColor.copy(
                                alpha = 0.81f
                            )
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = ButtonContainer,
                    titleContentColor = BlackTextColor
                ),
                windowInsets = WindowInsets(bottom = 43.dp, top = 43.dp),
                navigationIcon = {
                    if (isLogout) {
                        IconButton(onClick = {
                            memberViewModel.logout()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Logout,
                                contentDescription = null,
                                tint = BlackTextColor
                            )
                        }
                    }
                }

            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = BottomBackground
            ) {
                val myBucketsStyle = if (currentScreen == AllDestination.MYBUCKETS) {
                    CustomTypography.bottomInter18.copy(
                        color = BlackTextColor.copy(alpha = 0.9f),
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    CustomTypography.bottomInter18.copy(color = BlackTextColor.copy(alpha = 0.6f))
                }

                val ourBucketsStyle = if (currentScreen == AllDestination.OURBUCKETS) {
                    CustomTypography.bottomInter18.copy(
                        color = BlackTextColor.copy(alpha = 0.9f),
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    CustomTypography.bottomInter18.copy(color = BlackTextColor.copy(alpha = 0.6f))
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "My 버킷 투두",
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                appNavigationActionsAfterLogin.navigateToMyBuckets()
                            },
                        style = myBucketsStyle,
                        textAlign = TextAlign.Center
                    )
                    Divider(
                        modifier = Modifier
                            .width(1.dp)
                            .height(20.dp), color = GrayTextColor
                    )
                    Text(
                        text = "Our 버킷 공유",
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                appNavigationActionsAfterLogin.navigateToOurBuckets()
                            },
                        style = ourBucketsStyle,
                        textAlign = TextAlign.Center
                    )
                }
            }
        },

        floatingActionButton = {
            if (isFloatingButton) {
                Row(
                    modifier = Modifier
                        .padding(bottom = 20.dp, end = 10.dp)
                        .width(180.dp)
                        .height(36.dp)
                        .clickable {
                            setAddBucketDialog(true)
                        }
                        .background(
                            color = OrangeButtonContainer,
                            shape = RoundedCornerShape(30.dp)
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "버킷 투두 추가하기",
                        style = CustomTypography.bodyInterSmall16.copy(color = OrangeButtonContent),
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
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
            content(paddingValues)
        }
    }
}