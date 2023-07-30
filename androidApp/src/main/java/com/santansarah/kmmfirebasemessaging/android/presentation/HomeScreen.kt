package com.santansarah.kmmfirebasemessaging.android.presentation

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.santansarah.kmmfirebasemessaging.SharedRes
import com.santansarah.kmmfirebasemessaging.android.MyApplicationTheme
import com.santansarah.kmmfirebasemessaging.android.services.AppAnalyticsService
import com.santansarah.kmmfirebasemessaging.android.utils.APP_COLORS
import com.santansarah.kmmfirebasemessaging.android.utils.toColor
import com.santansarah.kmmfirebasemessaging.data.remote.models.products
import com.santansarah.kmmfirebasemessaging.presentation.home.HomeUIState
import com.santansarah.kmmfirebasemessaging.presentation.home.HomeViewModel
import com.santansarah.kmmfirebasemessaging.utils.ServiceResult
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = getViewModel()
) {

    val analyticsService = get<AppAnalyticsService>()
    val homeState = viewModel.homeUIState.collectAsStateWithLifecycle()

    HomeScreenLayout(
        homeState.value,
        analyticsService::completeTutorial,
        viewModel::onOnboardingScreenUpdated
    )

}

@Composable
fun HomeScreenLayout(
    homeUIState: HomeUIState,
    onAnalyticsEvent: () -> Unit,
    onOnboardingScreenUpdated: (Int) -> Unit
) {

    if (homeUIState.currentOnboardingScreen > 0) {
        if (!homeUIState.isOnboardingComplete)
            OnboardingScreen(
                homeUIState.currentOnboardingScreen,
                onAnalyticsEvent,
                onOnboardingScreenUpdated
            )
        else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(APP_COLORS.background.toColor())
            ) {

                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    painter =
                    painterResource(id = SharedRes.images.logo.drawableResId),
                    contentDescription = "Logo",
                    contentScale = ContentScale.FillWidth
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    text = stringResource(
                        SharedRes.strings.new_sign_in_heading.resourceId
                    ),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h5,
                    color = APP_COLORS.darkText.toColor()
                )


                Spacer(modifier = Modifier.height(20.dp))

                LazyColumn(
                    modifier = Modifier.padding(16.dp)
                ) {
                    items(products) {
                        Card(
                            modifier = Modifier
                                .padding(6.dp)
                                .fillMaxWidth()
                                .background(APP_COLORS.cardSurface.toColor(),
                                    RoundedCornerShape(6.dp)
                                )
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 6.dp, vertical = 10.dp)
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(.8f),
                                    text = it.title,
                                    color = APP_COLORS.darkText.toColor()
                                )
                                Column(
                                    modifier = Modifier.padding(end=4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = "Select Item",
                                        tint = APP_COLORS.darkText.toColor()
                                    )
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}

@Preview(
    showSystemUi = true, showBackground = true,
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    showSystemUi = true, showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun HomeScreenPreview() {

    val homeUIState = HomeUIState(
        currentOnboardingScreen = 1,
        isOnboardingComplete = true,
        products = ServiceResult.Success(products)
    )

    HomeScreenLayout(homeUIState, {}, {})

}