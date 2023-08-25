package com.santansarah.kmmfirebasemessaging.android.services

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.santansarah.kmmfirebasemessaging.android.domain.models.CurrentPermissionState
import com.santansarah.kmmfirebasemessaging.data.local.AppPreferencesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class PermissionManager(
    private val appPreferencesRepository: AppPreferencesRepository,
    private val scope: CoroutineScope
) : KoinComponent {

    // adb shell dumpsys package com.santansarah.kmmfirebasemessaging.android
    //  adb shell pm clear-permission-flags com.santansarah.kmmfirebasemessaging.android \
    //  android.permission.POST_NOTIFICATIONS user-set user-fixed

    private lateinit var resultLauncher: ActivityResultLauncher<Array<String>>
    private val _notificationsDenied = MutableStateFlow(false)
    private val appPreferences = appPreferencesRepository.userPreferencesFlow.onEach {
        _notificationsDenied.value = it.notificationsDenied
    }.launchIn(scope)

    private val _permissionState: MutableStateFlow<CurrentPermissionState> =
        MutableStateFlow(CurrentPermissionState.NeedsCheck)
    val permissionState = _permissionState.asStateFlow()

    @SuppressLint("InlinedApi")
    fun launchPermissionRequest() {
        resultLauncher.launch(
            arrayOf(Manifest.permission.POST_NOTIFICATIONS)
        )
    }

    fun registerLauncher(registry: ActivityResultRegistry) {
        resultLauncher = registry.register(
            "permissions",
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            // Handle Permission granted/rejected
            permissions.entries.forEach {
                val permissionName = it.key
                val isGranted = it.value
                if (isGranted) {
                    _permissionState.value = CurrentPermissionState.Granted
                } else {
                    if (_notificationsDenied.value)
                        _permissionState.value = CurrentPermissionState.Denied
                    else
                        _permissionState.value = CurrentPermissionState.ShowRational
                    scope.launch {
                        appPreferencesRepository.showNotifications(true)
                    }
                }
            }
        }
    }

    /*private fun registerLauncher(owner: LifecycleOwner, key: String = "permissions") = registry.register(
        key,
        owner,
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        // Handle Permission granted/rejected
        permissions.entries.forEach {
            val permissionName = it.key
            val isGranted = it.value
            if (isGranted) {
                _permissionState.value = CurrentPermissionState.Granted
            } else {
                _permissionState.value = CurrentPermissionState.Denied
            }
        }

    }*/

    fun checkPermissions(activity: ComponentActivity) {

        Log.d("permissions", "prefs: ${_notificationsDenied.value}")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            val permissionResults = ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.POST_NOTIFICATIONS
            )

            when (permissionResults) {
                PackageManager.PERMISSION_GRANTED -> {
                    _permissionState.value = CurrentPermissionState.Granted
                }

                PackageManager.PERMISSION_DENIED -> {
                    val showRational = shouldShowRequestPermissionRationale(
                        activity,
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                    if (showRational) {
                        _permissionState.value = CurrentPermissionState.ShowRational
                    } else {
                        if (_notificationsDenied.value)
                            _permissionState.value = CurrentPermissionState.Denied
                        else
                            _permissionState.value = CurrentPermissionState.NeedsCheck
                    }
                }

                else -> {
                    _permissionState.value = CurrentPermissionState.NeedsCheck
                }
            }
        } else {
            _permissionState.value = CurrentPermissionState.Granted
        }
    }

}