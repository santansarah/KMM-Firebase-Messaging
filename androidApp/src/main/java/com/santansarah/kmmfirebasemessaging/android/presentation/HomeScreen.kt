package com.santansarah.kmmfirebasemessaging.android.presentation

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.santansarah.kmmfirebasemessaging.SharedRes
import com.santansarah.kmmfirebasemessaging.android.services.AppAnalyticsService
import com.santansarah.kmmfirebasemessaging.data.remote.models.products
import com.santansarah.kmmfirebasemessaging.presentation.home.HomeUIState
import com.santansarah.kmmfirebasemessaging.presentation.home.HomeViewModel
import com.santansarah.kmmfirebasemessaging.utils.ServiceResult
import org.koin.androidx.compose.getViewModel
import org.koin.compose.rememberKoinInject

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = getViewModel(),
    onSignIn: () -> Unit,
    onSignOut: () -> Unit,
    isUserSignedIn: Boolean,
    onDetailClicked: (Int) -> Unit
) {

    val analyticsService: AppAnalyticsService = rememberKoinInject()
    val homeState = viewModel.homeUIState.collectAsStateWithLifecycle()

    rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()) {

    }

    HomeScreenLayout(
        homeState.value,
        analyticsService::completeTutorial,
        viewModel::onOnboardingScreenUpdated,
        onSignIn, onSignOut, isUserSignedIn, onDetailClicked
    )

}

@Composable
fun HomeScreenLayout(
    homeUIState: HomeUIState,
    onAnalyticsEvent: () -> Unit,
    onOnboardingScreenUpdated: (Int) -> Unit,
    onSignIn: () -> Unit,
    onSignOut: () -> Unit,
    isUserSignedIn: Boolean,
    onDetailClicked: (Int) -> Unit
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

                if (!isUserSignedIn) {
                    OutlinedButton(
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        onClick = { onSignIn() }
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxWidth(),
                            text = stringResource(
                                SharedRes.strings.new_sign_in_heading.resourceId
                            ),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }

                TextButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onSignOut() }) {
                    Text(text = "Sign Out", textAlign = TextAlign.Center)
                }

                Spacer(modifier = Modifier.height(20.dp))

                LazyColumn(
                    modifier = Modifier
                        .padding(12.dp)
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            RoundedCornerShape(12.dp)
                        )
                ) {
                    itemsIndexed(products) { idx, product ->
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 10.dp, end = 6.dp,
                                    top = 14.dp, bottom = 14.dp
                                )
                                .clickable {
                                    onDetailClicked(product.id)
                                }
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(4.dp),
                                model = product.image,
                                contentDescription = null,
                                contentScale = ContentScale.FillHeight
                            )
                            Text(
                                modifier = Modifier.fillMaxWidth(.8f),
                                text = product.title,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                            Column(
                                modifier = Modifier.padding(end = 4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Select Item",
                                    tint = MaterialTheme.colorScheme.onSecondary
                                )
                            }
                        }
                        if (idx < products.count() - 1)
                            Divider(thickness = 2.dp, color = Color.LightGray)
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

    HomeScreenLayout(homeUIState, {}, {}, {}, {}, false, {})

}