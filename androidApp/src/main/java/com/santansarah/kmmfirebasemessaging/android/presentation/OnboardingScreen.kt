package com.santansarah.kmmfirebasemessaging.android.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.santansarah.kmmfirebasemessaging.android.services.AppAnalyticsService
import com.santansarah.kmmfirebasemessaging.data.local.OnboardingScreenRepo
import com.santansarah.kmmfirebasemessaging.domain.models.OnboardingScreen
import org.koin.androidx.compose.get

@Composable
fun OnboardingScreen(
    currentScreen: Int,
    onScreenChanged: (Int) -> Unit
) {

    var analyticsService = get<AppAnalyticsService>()
    val thisScreen = OnboardingScreenRepo.screens[currentScreen-1]

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.padding(10.dp),
            painter = painterResource(id = thisScreen.headingIcon.drawableResId),
            contentDescription = "Onboarding Screen"
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = stringResource(id = thisScreen.headingText.resourceId),
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(30.dp))
        Button(onClick = {
            analyticsService.completeTutorial()
            onScreenChanged(thisScreen.currentScreen)
        }) {
            Text(text = "Next")
        }
    }

}

@Preview
@Composable
fun PreviewOnboardingScreen() {
    OnboardingScreen(
        currentScreen = 1,
        onScreenChanged = {}
    )
}