package com.example.appcenter_todolist.navigation

import androidx.navigation.NavController


class AppNavigationActions(private val navController: NavController) {
    fun navigateToWelcome() {
        navController.navigate(AllDestination.WELCOME) {
            popUpTo(AllDestination.WELCOME) { inclusive = true }
        }
    }

    fun navigateToRegister() {
        navController.navigate(AllDestination.REGISTER) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToRegisterSuccess() {
        navController.navigate(AllDestination.REGISTERSUCCESS) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToLogin() {
        navController.navigate(AllDestination.LOGIN) {
            launchSingleTop = true
            restoreState = true
        }
    }
    fun navigateToMyBuckets(){
        navController.navigate(AllDestination.MYBUCKETS){
            popUpTo(AllDestination.MYBUCKETS) {inclusive = true}
        }
    }

    fun navigateToMyDetailBucket(){
        navController.navigate(AllDestination.MYDETAILBUCKET){
            launchSingleTop = true
            restoreState = true
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

    fun navigateToOurMemberDetailBuckets(){
        navController.navigate(AllDestination.OURMEMBERDETAILBUCKETS){
            launchSingleTop = true
            restoreState = true
        }
    }

    fun getCurrentScreen(): String? {
        val currentDestination = navController.currentBackStackEntry?.destination
        return currentDestination?.route
    }

    fun popBackStack() {
        navController.popBackStack()
    }


}