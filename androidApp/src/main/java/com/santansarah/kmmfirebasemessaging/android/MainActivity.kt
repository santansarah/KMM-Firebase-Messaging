package com.santansarah.kmmfirebasemessaging.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.santansarah.kmmfirebasemessaging.Greeting
import com.santansarah.kmmfirebasemessaging.android.presentation.AppNavGraph
import com.santansarah.kmmfirebasemessaging.data.local.AppPreferences
import com.santansarah.kmmfirebasemessaging.data.local.AppPreferencesRepository
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.get
import org.koin.core.context.GlobalContext.get

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var appPreferencesRepository = get<AppPreferencesRepository>()

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

runBlocking {
    appPreferencesRepository.clear()
}
                    AppNavGraph(startDestination = "home")

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
