package com.santansarah.kmmfirebasemessaging.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.santansarah.kmmfirebasemessaging.android.theme.AppTheme
import com.santansarah.kmmfirebasemessaging.data.local.AppPreferencesRepository
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
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val scope = rememberCoroutineScope()
                    val isUserSignedIn = signInObserver.isUserSignedIn
                        .collectAsStateWithLifecycle().value

                    AppNavGraph(
                        startDestination = "home",
                        onSignIn = signInObserver::signUpUser,
                        onSignOut = {scope.launch { signInObserver.signOut()}},
                        isUserSignedIn = isUserSignedIn)
                }
            }
        }
    }
}
