package com.example.appcenter_todolist.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appcenter_todolist.navigation.AppNavigationActionsBeforeLogin
import com.example.appcenter_todolist.ui.theme.Background
import com.example.appcenter_todolist.ui.theme.ButtonContainer
import com.example.appcenter_todolist.ui.theme.ButtonContent
import com.example.appcenter_todolist.ui.theme.CustomTypography
import com.example.appcenter_todolist.ui.theme.Dimensions
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun WelcomeScreen(
    appNavigationActionsBeforeLogin: AppNavigationActionsBeforeLogin
) {

    val systemUiController = rememberSystemUiController()

    systemUiController.setSystemBarsColor(
        color = Background, // 원하는 색상으로 변경
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(horizontal = Dimensions.SidePadding, vertical = 37.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { appNavigationActionsBeforeLogin.navigateToRegister() },
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonContainer,
                contentColor = ButtonContent
            ),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(57.dp)
        ) {
            Text(
                text = "신규 회원가입",
                style = CustomTypography.bodyJamsil17,
            )
        }
        Spacer(modifier = Modifier.height(17.dp))
        Button(
            onClick = { appNavigationActionsBeforeLogin.navigateToLogin() },
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonContainer,
                contentColor = ButtonContent
            ),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(57.dp)
        ) {
            Text(
                text = "기존 유저 로그인",
                style = CustomTypography.bodyJamsil17,
            )
        }
    }
}