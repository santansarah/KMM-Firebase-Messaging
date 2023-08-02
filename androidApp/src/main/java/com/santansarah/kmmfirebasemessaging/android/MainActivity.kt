package com.santansarah.kmmfirebasemessaging.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.santansarah.kmmfirebasemessaging.android.presentation.account.SignInObserver
import com.santansarah.kmmfirebasemessaging.data.local.AppPreferencesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appPreferencesRepository = get<AppPreferencesRepository>()
        val signInObserver: SignInObserver by inject {parametersOf(this@MainActivity)}
        this.lifecycle.addObserver(signInObserver)

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val scope = rememberCoroutineScope()

                    AppNavGraph(
                        startDestination = "home",
                        onSignIn = signInObserver::signUpUser,
                        onSignOut = {scope.launch { signInObserver.signOut()}})
                }
            }
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
