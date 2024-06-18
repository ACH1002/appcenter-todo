package com.example.appcenter_todolist.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appcenter_todolist.navigation.AllDestination
import com.example.appcenter_todolist.navigation.AppNavigationActionsBeforeLogin
import com.example.appcenter_todolist.network.TokenExpirationEvent
import com.example.appcenter_todolist.ui.screens.login.LoginScreen
import com.example.appcenter_todolist.ui.screens.register.RegisterScreen
import com.example.appcenter_todolist.ui.screens.WelcomeScreen
import com.example.appcenter_todolist.ui.screens.mybucket.MyBucketsScreen
import com.example.appcenter_todolist.ui.screens.register.RegisterSuccessScreen
import com.example.appcenter_todolist.ui.theme.Appcenter_todolistTheme
import com.example.appcenter_todolist.viewmodel.AuthViewModel
import com.example.appcenter_todolist.viewmodel.MemberViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Appcenter_todolistTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    val navController = rememberNavController()
                    val appNavigationActionsBeforeLogin =
                        remember(navController) { AppNavigationActionsBeforeLogin(navController) }
                    val appNavigationActionsBeforeLoginAfterLogin =
                        remember(navController) { AppNavigationActionsBeforeLogin(navController) }
                    val authViewModel : AuthViewModel = koinViewModel()
                    val isTokenValid by authViewModel.isTokenValid.collectAsState()

                    LaunchedEffect(Unit){
                        authViewModel.checkTokenValidity()
                    }

                    LaunchedEffect(isTokenValid) {
                        TokenExpirationEvent.expired.postValue(true)
                    }

                    val memberViewModel : MemberViewModel = koinViewModel()
                    val tokenExpired = TokenExpirationEvent.expired.observeAsState()
                    if (isTokenValid == true || tokenExpired.value == false){
                        NavHost(
                            navController = navController,
                            startDestination = AllDestination.MYBUCKETS
                        ) {
                            composable(AllDestination.MYBUCKETS) {
                                MyBucketsScreen(
                                    appNavigationActionsBeforeLoginAfterLogin
                                ) }
                            composable(AllDestination.OURBUCKETS) {

                            }
                            composable(AllDestination.OURDETAILBUCKETS) {

                            }
                        }
                    } else {
                        NavHost(
                            navController = navController,
                            startDestination = AllDestination.WELCOME
                        ) {
                            composable(AllDestination.WELCOME) { WelcomeScreen(appNavigationActionsBeforeLogin) }
                            composable(AllDestination.LOGIN) {
                                LoginScreen(
                                    appNavigationActionsBeforeLogin,
                                    memberViewModel = memberViewModel
                                )
                            }
                            composable(AllDestination.REGISTER) {
                                RegisterScreen(
                                    appNavigationActionsBeforeLogin,
                                    memberViewModel = memberViewModel
                                )
                            }
                            composable(AllDestination.REGISTERSUCCESS) {
                                RegisterSuccessScreen(
                                    appNavigationActionsBeforeLogin
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}
