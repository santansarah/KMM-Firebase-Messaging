package com.santansarah.kmmfirebasemessaging.android

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.santansarah.kmmfirebasemessaging.android.presentation.HomeScreen
import com.santansarah.kmmfirebasemessaging.android.presentation.SignInScreen
import com.santansarah.kmmfirebasemessaging.domain.AppDeepLink
import kotlinx.coroutines.CoroutineScope
import kotlin.reflect.KSuspendFunction0

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String,
    onSignIn: () -> Unit,
    onSignOut: () -> Unit,
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable("home") {
            HomeScreen(onSignIn = onSignIn, onSignOut = onSignOut)
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
