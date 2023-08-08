package com.santansarah.kmmfirebasemessaging.android.presentation.detail

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.messaging.FirebaseMessaging
import com.santansarah.kmmfirebasemessaging.SharedRes
import com.santansarah.kmmfirebasemessaging.android.theme.AppTheme
import com.santansarah.kmmfirebasemessaging.data.remote.models.Product
import com.santansarah.kmmfirebasemessaging.data.remote.models.products
import com.santansarah.kmmfirebasemessaging.presentation.home.HomeViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.koin.androidx.compose.getViewModel

@Composable
fun ProductDetailScreen(
    viewModel: HomeViewModel = getViewModel()
) {




}

@Composable
fun ProductDetailLayout(
    product: Product
) {
    var token: String
    LaunchedEffect(key1 = true ){
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
            Image(
                modifier = Modifier
                    .size(72.dp)
                    .padding(end = 10.dp),
                painter = painterResource(id = SharedRes.images.account.drawableResId),
                contentDescription = ""
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
                scope.launch {
                    //orderRepository.insertNewOrder(Order())
                }
            }) {
            Text(text = "Place Order")
        }

    }
}

@Preview
@Composable
fun ProductDetailLayoutPreview() {
    AppTheme {
        ProductDetailLayout(products.first())
    }
}