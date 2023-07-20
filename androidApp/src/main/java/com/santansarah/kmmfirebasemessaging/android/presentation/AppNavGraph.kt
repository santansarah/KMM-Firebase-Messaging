package com.santansarah.kmmfirebasemessaging.android.presentation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink

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
        /*composable(
            "dealsSceen",
            deepLinks = listOf(navDeepLink {
                uriPattern = "myapp://deals"
            }),
        ) {
            Deals()
        }
        composable(
            route = "trackScreen/{orderId}",
            arguments = listOf(navArgument("orderId") { type = NavType.StringType }),
            deepLinks = listOf(navDeepLink {
                uriPattern = "myapp://track/{orderId}"
            }),
        ) {
            val params = it.arguments
            TrackOrder(orderId = params?.getString("orderId"))
        }*/
    }
}
