package com.example.appcenter_todolist.ui.screens.register

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.sp
import com.example.appcenter_todolist.navigation.AppNavigationActionsBeforeLogin
import com.example.appcenter_todolist.ui.components.ButtonBeforeLogin
import com.example.appcenter_todolist.ui.components.LoginTextField
import com.example.appcenter_todolist.ui.components.ToolBarBeforeLogin
import com.example.appcenter_todolist.ui.theme.BlackTextColor
import com.example.appcenter_todolist.ui.theme.CustomTypography
import com.example.appcenter_todolist.ui.theme.Dimensions
import com.example.appcenter_todolist.viewmodel.MemberViewModel

@Composable
fun RegisterScreen(
    appNavigationActionsBeforeLogin: AppNavigationActionsBeforeLogin,
    memberViewModel: MemberViewModel
) {
    var signupEmail by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    var signupPassword by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    var signupNickname by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    val signupSuccess by memberViewModel.signupSuccess.collectAsState()

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
                    .weight(1f)
                    .padding(top = 64.dp)
            ) {
                Text(
                    text = "아이디, 비밀번호, 닉네임을 입력해주세요",
                    style = CustomTypography.headerJamsil21.copy(color = BlackTextColor, fontSize = 20.sp),
                    modifier = Modifier
                        .padding(start = 3.dp)
                )
                Spacer(modifier = Modifier.height(21.dp))
                LoginTextField(
                    placeholder = "이메일",
                    content = signupEmail,
                    updateContent = { signupEmail = it },
                    topLabel = "아이디"
                )
                Spacer(modifier = Modifier.height(21.dp))
                LoginTextField(
                    placeholder = "비밀번호 입력",
                    content = signupPassword,
                    updateContent = { signupPassword = it },
                    topLabel = "비밀번호",
                    isPassword = true
                )
                Spacer(modifier = Modifier.height(21.dp))
                LoginTextField(
                    placeholder = "닉네임 입력",
                    content = signupNickname,
                    updateContent = { signupNickname = it },
                    topLabel = "닉네임",
                    isDone = true
                )
            }
            ButtonBeforeLogin(
                content = "회원가입",
                onclick = {
                    Log.d("email, pw, nickname", "${signupEmail.text} , ${signupPassword.text}, ${signupNickname.text}")
//                    memberViewModel.signup(signupMemberReq = SignupMemberReq(
//                        email = signupEmail.text,
//                        password = signupPassword.text,
//                        nickname = signupNickname.text
//                    ))
                }
            )
        }
    }

    LaunchedEffect(signupSuccess){
        if (signupSuccess == true){
            memberViewModel.clearSignUp()
            appNavigationActionsBeforeLogin.navigateToRegisterSuccess()
        }
    }
}