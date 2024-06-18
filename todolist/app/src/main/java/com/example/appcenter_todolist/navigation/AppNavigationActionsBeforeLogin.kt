package com.example.appcenter_todolist.navigation

import androidx.navigation.NavController
import com.example.appcenter_todolist.navigation.AllDestination.LOGIN
import com.example.appcenter_todolist.navigation.AllDestination.MYBUCKETS
import com.example.appcenter_todolist.navigation.AllDestination.OURBUCKETS
import com.example.appcenter_todolist.navigation.AllDestination.OURDETAILBUCKETS
import com.example.appcenter_todolist.navigation.AllDestination.REGISTER
import com.example.appcenter_todolist.navigation.AllDestination.REGISTERNICKNAME
import com.example.appcenter_todolist.navigation.AllDestination.REGISTERSUCCESS
import com.example.appcenter_todolist.navigation.AllDestination.WELCOME

class AppNavigationActionsBeforeLogin(private val navController: NavController) {


    fun navigateToWelcome(){
        navController.navigate(WELCOME){
            popUpTo(WELCOME) {inclusive = true}
        }
    }
    fun navigateToRegister(){
        navController.navigate(REGISTER){
            launchSingleTop = true
            restoreState = true
        }

    }

    fun navigateToRegisterNickname(){
        navController.navigate(REGISTERNICKNAME){
            launchSingleTop = true
            restoreState = true
        }

    }
    fun navigateToRegisterSuccess(){
        navController.navigate(REGISTERSUCCESS){
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToLogin(){
        navController.navigate(LOGIN){
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToMyBuckets(){
        navController.navigate(MYBUCKETS){
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToOurBuckets(){
        navController.navigate(OURBUCKETS){
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToOurDetailBuckets(){
        navController.navigate(OURDETAILBUCKETS){
            launchSingleTop = true
            restoreState = true
        }
    }

    fun popBackStack() {
        navController.popBackStack()
    }

}