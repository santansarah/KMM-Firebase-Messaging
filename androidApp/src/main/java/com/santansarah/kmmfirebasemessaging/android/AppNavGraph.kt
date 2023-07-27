package com.santansarah.kmmfirebasemessaging.android

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.santansarah.kmmfirebasemessaging.android.presentation.HomeScreen
import com.santansarah.kmmfirebasemessaging.android.presentation.SignInScreen

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable("home") {
            HomeScreen()
        }
        composable(
            "signin",
            deepLinks = listOf(navDeepLink {
                uriPattern = "kmm://signin"
            }),
        ) {
            SignInScreen()
        }
    }
}
