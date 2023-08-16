package com.santansarah.kmmfirebasemessaging.android.presentation.detail

import android.util.Log
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.google.firebase.messaging.FirebaseMessaging
import com.santansarah.kmmfirebasemessaging.android.theme.AppTheme
import com.santansarah.kmmfirebasemessaging.data.remote.models.Product
import com.santansarah.kmmfirebasemessaging.data.remote.models.products
import com.santansarah.kmmfirebasemessaging.presentation.details.DetailsViewModel
import com.santansarah.kmmfirebasemessaging.utils.ServiceResult
import kotlinx.coroutines.tasks.await
import java.util.UUID

@Composable
fun ProductDetailScreen(
    viewModel: DetailsViewModel
) {

    val uiState = viewModel.detailsState.collectAsStateWithLifecycle()

    when (val currentProduct = uiState.value.selectedProduct) {
        is ServiceResult.Success<*> -> {
            ProductDetailLayout(product = currentProduct.data as Product,
                viewModel::insertOrUpdateOrder)
        }
        else -> {
            CircularProgressIndicator()
        }
    }

}

@Composable
fun ProductDetailLayout(
    product: Product,
    onOrderPlaced: (String, Boolean) -> Unit
) {
    var token: String
    LaunchedEffect(key1 = true) {
        token = FirebaseMessaging.getInstance().token.await()
        Log.d("test", "token: $token")
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
        ProductDetailLayout(products.first()) { _,_ ->}
    }
}