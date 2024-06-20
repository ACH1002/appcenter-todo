package com.example.appcenter_todolist.ui.components.toolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.appcenter_todolist.navigation.AppNavigationActions
import com.example.appcenter_todolist.ui.theme.Background
import com.example.appcenter_todolist.ui.theme.BlackTextColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolBarBeforeLogin(
    appNavigationActions: AppNavigationActions,
    content: @Composable (PaddingValues) -> Unit
) {
    val systemUiController = rememberSystemUiController()



    systemUiController.setSystemBarsColor(
        color = Background, // 원하는 색상으로 변경
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { appNavigationActions.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Background,
                    navigationIconContentColor = BlackTextColor
                )
            )
        },
    ) { paddingValues ->
        Box(modifier = Modifier
            .padding(paddingValues)
            .background(color = Background)
        ){
            content(paddingValues)
        }
    }
}