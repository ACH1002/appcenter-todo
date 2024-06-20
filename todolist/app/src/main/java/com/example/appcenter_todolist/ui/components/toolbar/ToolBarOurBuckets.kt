package com.example.appcenter_todolist.ui.components.toolbar

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.appcenter_todolist.navigation.AllDestination
import com.example.appcenter_todolist.navigation.AppNavigationActions
import com.example.appcenter_todolist.ui.theme.Background
import com.example.appcenter_todolist.ui.theme.BlackTextColor
import com.example.appcenter_todolist.ui.theme.BottomBackground
import com.example.appcenter_todolist.ui.theme.ButtonContainer
import com.example.appcenter_todolist.ui.theme.CustomTypography
import com.example.appcenter_todolist.ui.theme.GrayBorderColor
import com.example.appcenter_todolist.ui.theme.GrayTextColor
import com.example.appcenter_todolist.viewmodel.BucketViewModel
import com.example.appcenter_todolist.viewmodel.MemberViewModel
import com.example.appcenter_todolist.viewmodel.SelectedMemberState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolBarOurBuckets(
    appNavigationActions: AppNavigationActions,
    bucketViewModel: BucketViewModel,
    memberViewModel: MemberViewModel,
    isBack: Boolean = false,
    content: @Composable (PaddingValues) -> Unit,
) {
    val currentScreen = appNavigationActions.getCurrentScreen()
    val focusManager = LocalFocusManager.current

    var searchMemberContent by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    val selectedMemberState by memberViewModel.selectedMemberState.collectAsState()


    //연속으로 뒤로가기 누를 경우에 대한 대처
    var isBackButtonPressed by remember { mutableStateOf(false) }

    BackHandler {
        if (!isBackButtonPressed) {
            isBackButtonPressed = true
            Log.d("isBack", "isBack")
            appNavigationActions.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (isBack){
                        val nickname = (selectedMemberState as SelectedMemberState.Success).selectedMember.nickname
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(color = Color(0xFFd67419))) {
                                    append(nickname)
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
                    } else {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 30.dp)
                                .shadow(4.dp, shape = RoundedCornerShape(15.dp))
                        ) {
                            OutlinedTextField(
                                value = searchMemberContent,
                                onValueChange = { searchMemberContent = it },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = GrayBorderColor,
                                    disabledBorderColor = GrayBorderColor,
                                    errorBorderColor = GrayBorderColor,
                                    unfocusedBorderColor = GrayBorderColor,
                                    containerColor = Background
                                ),
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = null,
                                        tint = GrayBorderColor
                                    )
                                },
                                shape = RoundedCornerShape(15.dp),
                                textStyle = CustomTypography.textFieldInter15.copy(color = BlackTextColor),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(
                                    capitalization = KeyboardCapitalization.None,
                                    autoCorrect = true,
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        bucketViewModel.searchBucketByMember(nickname = searchMemberContent.text)
                                        memberViewModel.setSearchedNickname(nickname = searchMemberContent.text)
                                        focusManager.clearFocus()
                                    }
                                ),
                                trailingIcon = {
                                    if (searchMemberContent.text.isNotEmpty()) {
                                        IconButton(onClick = {
                                            searchMemberContent = TextFieldValue("")
                                            bucketViewModel.clearSearchedMemberBucketListState()
                                            focusManager.clearFocus()
                                        }) {
                                            Icon(
                                                imageVector = Icons.Default.Close,
                                                contentDescription = null
                                            )
                                        }
                                    }
                                }
                            )
                        }
                    }

                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = ButtonContainer,
                    titleContentColor = BlackTextColor
                ),
                windowInsets = WindowInsets(bottom = 43.dp, top = 43.dp),

                navigationIcon = {
                    if (isBack) {
                        IconButton(onClick = {
                            if (!isBackButtonPressed) {
                                isBackButtonPressed = true
                                Log.d("isBack", "isBack")
                                appNavigationActions.popBackStack()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
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
                                appNavigationActions.navigateToMyBuckets()
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
                                appNavigationActions.navigateToOurBuckets()
                            },
                        style = ourBucketsStyle,
                        textAlign = TextAlign.Center
                    )
                }
            }
        },
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