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
import com.example.appcenter_todolist.navigation.AppNavigationActionsAfterLogin
import com.example.appcenter_todolist.navigation.AppNavigationActionsBeforeLogin
import com.example.appcenter_todolist.network.TokenExpirationEvent
import com.example.appcenter_todolist.ui.screens.login.LoginScreen
import com.example.appcenter_todolist.ui.screens.register.RegisterScreen
import com.example.appcenter_todolist.ui.screens.WelcomeScreen
import com.example.appcenter_todolist.ui.screens.mybucket.MyBucketDetailScreen
import com.example.appcenter_todolist.ui.screens.mybucket.MyBucketsScreen
import com.example.appcenter_todolist.ui.screens.ourbucket.OurBucketDetailScreen
import com.example.appcenter_todolist.ui.screens.ourbucket.OurBucketsScreen
import com.example.appcenter_todolist.ui.screens.ourbucket.OurMemberDetailBucketList
import com.example.appcenter_todolist.ui.screens.register.RegisterSuccessScreen
import com.example.appcenter_todolist.ui.theme.Appcenter_todolistTheme
import com.example.appcenter_todolist.viewmodel.AuthViewModel
import com.example.appcenter_todolist.viewmodel.BucketViewModel
import com.example.appcenter_todolist.viewmodel.CommentViewModel
import com.example.appcenter_todolist.viewmodel.MemberViewModel
import com.example.appcenter_todolist.viewmodel.TodoViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Appcenter_todolistTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    val systemUiController = rememberSystemUiController()
                    systemUiController.isNavigationBarVisible = false

                    val navController = rememberNavController()
                    val appNavigationActionsBeforeLogin =
                        remember(navController) { AppNavigationActionsBeforeLogin(navController) }
                    val appNavigationActionsAfterLogin =
                        remember(navController) { AppNavigationActionsAfterLogin(navController) }
                    val authViewModel: AuthViewModel = koinViewModel()
                    val memberViewModel: MemberViewModel = koinViewModel()
                    val loginState by memberViewModel.loginState.collectAsState()
                    val bucketViewModel: BucketViewModel = koinViewModel()
                    val todoViewModel: TodoViewModel = koinViewModel()
                    val commentViewModel: CommentViewModel = koinViewModel()

                    val isCheckValidity by memberViewModel.isCheckValidity.collectAsState()

                    if (!isCheckValidity) {
                        LaunchedEffect(Unit) {
                            memberViewModel.checkTokenValidity()
                            memberViewModel.isCheckValidity()
                        }
                    }

                    val tokenExpired = TokenExpirationEvent.expired.observeAsState()

                    LaunchedEffect(tokenExpired.value) {
                        if (tokenExpired.value == true) {
                            memberViewModel.checkTokenValidity()
                            navController.navigate(AllDestination.WELCOME) {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                            }
                        }
                    }

                    if (loginState && tokenExpired.value == false) {
                        NavHost(
                            navController = navController,
                            startDestination = AllDestination.MYBUCKETS
                        ) {
                            composable(AllDestination.MYBUCKETS) {
                                MyBucketsScreen(
                                    appNavigationActionsAfterLogin = appNavigationActionsAfterLogin,
                                    bucketViewModel = bucketViewModel,
                                    memberViewModel = memberViewModel
                                )
                            }
                            composable(AllDestination.MYDETAILBUCKET) {
                                MyBucketDetailScreen(
                                    appNavigationActionsAfterLogin = appNavigationActionsAfterLogin,
                                    bucketViewModel = bucketViewModel,
                                    todoViewModel = todoViewModel,
                                    commentViewModel = commentViewModel,
                                    memberViewModel = memberViewModel,
                                )
                            }
                            composable(AllDestination.OURBUCKETS) {
                                OurBucketsScreen(
                                    appNavigationActionsAfterLogin = appNavigationActionsAfterLogin,
                                    memberViewModel = memberViewModel,
                                    bucketViewModel = bucketViewModel
                                )
                            }
                            composable(AllDestination.OURDETAILBUCKETS) {
                                OurBucketDetailScreen(
                                    appNavigationActionsAfterLogin = appNavigationActionsAfterLogin,
                                    bucketViewModel = bucketViewModel,
                                    todoViewModel = todoViewModel,
                                    commentViewModel = commentViewModel,
                                    memberViewModel = memberViewModel
                                )
                            }
                            composable(AllDestination.OURMEMBERDETAILBUCKETS) {
                                OurMemberDetailBucketList(
                                    appNavigationActionsAfterLogin = appNavigationActionsAfterLogin,
                                    bucketViewModel = bucketViewModel,
                                    memberViewModel = memberViewModel
                                )
                            }
                        }
                    } else {
                        NavHost(
                            navController = navController,
                            startDestination = AllDestination.WELCOME
                        ) {
                            composable(AllDestination.WELCOME) {
                                WelcomeScreen(appNavigationActionsBeforeLogin)
                            }
                            composable(AllDestination.LOGIN) {
                                LoginScreen(
                                    appNavigationActionsBeforeLogin,
                                    appNavigationActionsAfterLogin,
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
