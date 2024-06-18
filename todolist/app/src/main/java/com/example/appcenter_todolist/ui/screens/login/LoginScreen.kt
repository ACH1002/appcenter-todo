package com.example.appcenter_todolist.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.appcenter_todolist.model.member.LoginMemberReq
import com.example.appcenter_todolist.navigation.AppNavigationActionsBeforeLogin
import com.example.appcenter_todolist.ui.components.ButtonBeforeLogin
import com.example.appcenter_todolist.ui.components.LoginTextField
import com.example.appcenter_todolist.ui.components.ToolBarBeforeLogin
import com.example.appcenter_todolist.ui.theme.Dimensions
import com.example.appcenter_todolist.viewmodel.MemberViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    appNavigationActionsBeforeLogin: AppNavigationActionsBeforeLogin,
    memberViewModel: MemberViewModel
) {
    val loginSuccess by memberViewModel.loginSuccess.collectAsState()

    var loginEmail by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    var loginPassword by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    ToolBarBeforeLogin(
        appNavigationActionsBeforeLogin = appNavigationActionsBeforeLogin
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = Dimensions.SidePadding,
                    end = Dimensions.SidePadding,
                    bottom = 37.dp
                ),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoginTextField(
                    placeholder = "이메일",
                    content = loginEmail,
                    updateContent = { loginEmail = it }
                )
                Spacer(modifier = Modifier.height(20.dp))
                LoginTextField(
                    placeholder = "비밀번호",
                    content = loginPassword,
                    updateContent = { loginPassword = it },
                    isPassword = true,
                    isDone = true
                )
            }
            ButtonBeforeLogin(
                content = "로그인하기",
                onclick = {
                    memberViewModel.login(
                        loginMemberReq = LoginMemberReq(
                            email = loginEmail.text,
                            password = loginPassword.text
                        )
                    )
                }
            )
        }
    }


    LaunchedEffect(memberViewModel.loginSuccess){
        if (loginSuccess == true) {
            memberViewModel.clearLogin()
            appNavigationActionsBeforeLogin.navigateToMyBuckets()
        }
    }
}