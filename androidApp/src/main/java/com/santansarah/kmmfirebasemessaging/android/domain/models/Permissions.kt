package com.santansarah.kmmfirebasemessaging.android.domain.models

data class PermissionEvents(
    val onHasPermission: (Boolean) -> Unit,
    val onShowRational: (Boolean) -> Unit,
)

data class NotificationPermissions(
    val currentState: CurrentPermissionState,

)

sealed interface CurrentPermissionState {
    object NeedsCheck: CurrentPermissionState
    object ShowRational: CurrentPermissionState
    object Granted: CurrentPermissionState
    object Denied: CurrentPermissionState
}