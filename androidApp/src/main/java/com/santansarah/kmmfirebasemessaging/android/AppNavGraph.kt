package com.santansarah.kmmfirebasemessaging.android

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.santansarah.kmmfirebasemessaging.android.presentation.HomeScreen
import com.santansarah.kmmfirebasemessaging.android.presentation.account.SignInScreen
import com.santansarah.kmmfirebasemessaging.domain.AppDeepLink

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String,
    onSignIn: () -> Unit,
    onSignOut: () -> Unit,
    isUserSignedIn: Boolean
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable("home") {
            HomeScreen(onSignIn = onSignIn, onSignOut = onSignOut, isUserSignedIn = isUserSignedIn)
        }
        composable(
            "signin",
            deepLinks = listOf(navDeepLink {
                uriPattern = AppDeepLink.pathFromType(AppDeepLink.SIGNIN)
            }),
        ) {
            SignInScreen()
        }
    }
}
