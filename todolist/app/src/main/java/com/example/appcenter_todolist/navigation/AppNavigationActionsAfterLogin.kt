package com.example.appcenter_todolist.navigation

import androidx.navigation.NavController


class AppNavigationActionsAfterLogin(private val navController: NavController) {


    fun navigateToMyBuckets(){
        navController.navigate(AllDestination.MYBUCKETS){
            popUpTo(AllDestination.MYBUCKETS) {inclusive = true}
        }
    }

    fun navigateToOurBuckets(){
        navController.navigate(AllDestination.OURBUCKETS){
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToOurDetailBuckets(){
        navController.navigate(AllDestination.OURDETAILBUCKETS){
            launchSingleTop = true
            restoreState = true
        }
    }

    fun popBackStack() {
        navController.popBackStack()
    }

}