package com.example.appcenter_todolist.ui.screens.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.appcenter_todolist.navigation.AppNavigationActions
import com.example.appcenter_todolist.ui.components.button.ButtonBeforeLogin
import com.example.appcenter_todolist.ui.components.toolbar.ToolBarBeforeLogin
import com.example.appcenter_todolist.ui.theme.BlackTextColor
import com.example.appcenter_todolist.ui.theme.CustomTypography
import com.example.appcenter_todolist.ui.theme.Dimensions

@Composable
fun RegisterSuccessScreen(
    appNavigationActions: AppNavigationActions
) {
    ToolBarBeforeLogin(appNavigationActions = appNavigationActions) {
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
                Text(
                    text = "수고하셨습니다!",
                    style = CustomTypography.headerJamsil21.copy(
                        color = BlackTextColor,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            ButtonBeforeLogin(
                content = "로그인 바로가기",
                onclick = {
                    appNavigationActions.navigateToLogin()
                }
            )
        }
    }
}