package com.santansarah.kmmfirebasemessaging.android.presentation.detail

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivityResultRegistryOwner
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.santansarah.kmmfirebasemessaging.android.domain.models.CurrentPermissionState
import com.santansarah.kmmfirebasemessaging.android.services.PermissionManager
import com.santansarah.kmmfirebasemessaging.android.theme.AppTheme
import com.santansarah.kmmfirebasemessaging.data.remote.models.Product
import com.santansarah.kmmfirebasemessaging.data.remote.models.products
import com.santansarah.kmmfirebasemessaging.presentation.details.DetailsViewModel
import com.santansarah.kmmfirebasemessaging.utils.ServiceResult
import org.koin.compose.koinInject
import java.util.UUID

@Composable
fun ProductDetailScreen(
    viewModel: DetailsViewModel,
    permissionManager: PermissionManager = koinInject()
) {

    val uiState = viewModel.detailsState.collectAsStateWithLifecycle()

    Log.d("permissions", permissionManager.toString())

    val activity = LocalContext.current as ComponentActivity
    val activityResultRegistry = checkNotNull(LocalActivityResultRegistryOwner.current) {
        "No ActivityResultRegistryOwner was provided via LocalActivityResultRegistryOwner"
    }.activityResultRegistry

    LaunchedEffect(Unit) {
        permissionManager.registerLauncher(activityResultRegistry)
        permissionManager.checkPermissions(activity)
    }

    val permissionState = permissionManager.permissionState.collectAsStateWithLifecycle()

    when (val currentProduct = uiState.value.selectedProduct) {
        is ServiceResult.Success<*> -> {
            ProductDetailLayout(
                product = currentProduct.data as Product,
                viewModel::insertOrUpdateOrder,
                permissionState.value,
                permissionManager::launchPermissionRequest,
            )
        }

        else -> {
            CircularProgressIndicator()
        }
    }

}

@Composable
fun ProductDetailLayout(
    product: Product,
    onOrderPlaced: (String, Boolean) -> Unit,
    permissionState: CurrentPermissionState,
    onPermissionsClicked: () -> Unit,
) {

    var openBottomSheet by rememberSaveable { mutableStateOf(true) }

    Log.d("permissions", permissionState.toString())

    when (permissionState) {
        is CurrentPermissionState.NeedsCheck,
        CurrentPermissionState.ShowRational -> {
            ShowPermissionsSheet(
                openBottomSheet,
                permissionState,
                onPermissionsClicked,
                { openBottomSheet = false },
            )
        }

        else -> {
            openBottomSheet = false
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = "Order Details",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
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
                modifier = Modifier
                    .weight(2f)
                    .padding(16.dp),
                text = product.description,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                modifier = Modifier.padding(4.dp),
                text = product.price.toString(),
                textAlign = TextAlign.End
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        val scope = rememberCoroutineScope()
        Button(
            modifier = Modifier.fillMaxWidth(.7f),
            onClick = {
                onOrderPlaced(UUID.randomUUID().toString(), false)
            }) {
            Text(text = "Place Order")
        }

    }
}

@Preview
@Composable
fun ProductDetailLayoutPreview() {
    AppTheme {
        ProductDetailLayout(products.first(), { _, _ -> },
            CurrentPermissionState.NeedsCheck,
            {})
    }
}