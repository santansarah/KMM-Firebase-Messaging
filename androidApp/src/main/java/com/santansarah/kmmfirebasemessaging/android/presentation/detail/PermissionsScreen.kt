package com.santansarah.kmmfirebasemessaging.android.presentation.detail

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.santansarah.kmmfirebasemessaging.android.domain.models.CurrentPermissionState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowPermissionsSheet(
    shouldShowSheet: Boolean,
    permissionState: CurrentPermissionState,
    onPermissionsClicked: () -> Unit,
    onDismiss: () -> Unit,
) {

    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState()

// Sheet content
    if (shouldShowSheet) {
        val windowInsets = BottomSheetDefaults.windowInsets

        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = bottomSheetState,
            windowInsets = windowInsets
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier.padding(end = 6.dp, bottom = 6.dp)
                        .size(42.dp),
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Order Notifications"
                )
                Text(text = "Get Order Notifications",
                    style = MaterialTheme.typography.titleLarge)
            }
            Divider(color = Color.DarkGray, thickness=2.dp)

            val text = if (permissionState == CurrentPermissionState.ShowRational) {
                "Are you sure you don't want order notifications? Click again to officially " +
                        "opt out, and we won't bother you again."
            } else {
                "We can send important notifications about your orders, including when " +
                        "your order has shipped. Click to configure your notification preferences."
            }

            Text(
                modifier = Modifier.padding(16.dp),
                text = (text)
            )

            val imSure = if (permissionState == CurrentPermissionState.ShowRational) {
                "I'm sure, opt-out"
            } else {
                "Allow or Opt-Out"
            }

            TextButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { onPermissionsClicked() }) {
                Text(text = imSure)
            }
            TextButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    onDismiss()
                }) {
                Text(text = "I'm not sure, ask me later")
            }
            Spacer(Modifier.height(30.dp))
        }
    }


}

@Preview(showBackground = true)
@Composable
fun BottomSheetPreview() {
    ShowPermissionsSheet(true, CurrentPermissionState.NeedsCheck, {}, {})
}