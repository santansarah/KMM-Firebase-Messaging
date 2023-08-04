package com.santansarah.kmmfirebasemessaging.android.presentation

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.santansarah.kmmfirebasemessaging.android.utils.ThemeColors
import com.santansarah.kmmfirebasemessaging.android.utils.toColor
import com.santansarah.kmmfirebasemessaging.data.local.OnboardingScreenRepo

@Composable
fun OnboardingScreen(
    currentScreen: Int,
    onAnalyticsEvent: () -> Unit,
    onScreenChanged: (Int) -> Unit
) {

    val thisScreen = OnboardingScreenRepo.screens[currentScreen - 1]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ThemeColors.background.toColor()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.padding(10.dp),
            painter = painterResource(id = thisScreen.headingIcon.drawableResId),
            contentDescription = "Onboarding Screen"
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = thisScreen.headingText.resourceId),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            color = ThemeColors.darkText.toColor()
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            colors = ButtonDefaults.buttonColors(
                //backgroundColor = ThemeColors.primary.toColor(),
                //contentColor = ThemeColors.lightText.toColor()
            ),
            onClick = {
                onAnalyticsEvent()
                onScreenChanged(thisScreen.currentScreen)
            }) {
            Text(text = "Next")
        }
    }

}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun PreviewOnboardingScreen() {
    OnboardingScreen(
        currentScreen = 1,
        onAnalyticsEvent = {},
        onScreenChanged = {}
    )
}