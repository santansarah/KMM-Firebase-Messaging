package com.santansarah.kmmfirebasemessaging.android

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.santansarah.kmmfirebasemessaging.android.presentation.HomeScreen
import com.santansarah.kmmfirebasemessaging.android.presentation.account.SignInScreen
import com.santansarah.kmmfirebasemessaging.android.presentation.detail.ProductDetailScreen
import com.santansarah.kmmfirebasemessaging.android.services.PermissionManager
import com.santansarah.kmmfirebasemessaging.domain.AppDeepLink
import com.santansarah.kmmfirebasemessaging.presentation.details.DetailsViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.compose.koinInject
import org.koin.core.context.GlobalContext.get
import org.koin.core.parameter.parametersOf

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
            HomeScreen(onSignIn = onSignIn,
                onSignOut = onSignOut,
                isUserSignedIn = isUserSignedIn,
                onDetailClicked = { productId ->
                    navController.navigate("details/$productId")
                }
            )
        }
        composable(
            "signin",
            deepLinks = listOf(navDeepLink {
                uriPattern = AppDeepLink.pathFromType(AppDeepLink.SIGNIN)
            }),
        ) {
            SignInScreen()
        }
        composable(
            "details/{productId}",
            arguments = listOf(navArgument("productId")
            { type = NavType.IntType })
        ) {

            val viewModel: DetailsViewModel =
                getViewModel(parameters = {
                    parametersOf(
                        it.arguments?.getInt("productId")
                    )
                })

            ProductDetailScreen(viewModel = viewModel)

        }
    }
}
