package com.santansarah.kmmfirebasemessaging.android

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.MutableState
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.santansarah.kmmfirebasemessaging.android.services.SignInService
import com.santansarah.kmmfirebasemessaging.utils.SignUpHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInObserver @Inject constructor(
    private val activity: Context,
    private val signInService: SignInService
) : DefaultLifecycleObserver {

    private lateinit var getAuthResults: ActivityResultLauncher<Intent>

    private var _isUserSignedIn = MutableStateFlow(false)
    val isUserSignedIn = _isUserSignedIn.asStateFlow()

    override fun onCreate(owner: LifecycleOwner) {
        val registry = (activity as ComponentActivity).activityResultRegistry

        owner.lifecycle.coroutineScope.launch {
            signInService
                .isUserSignedIn(this).collect {
                    _isUserSignedIn.value = it
                }
        }

        getAuthResults = registry.register(
            SignUpHelper.SIGN_UP_REQUEST_CODE, owner,
            FirebaseAuthUIActivityResultContract()
        ) { result ->
            owner.lifecycle.coroutineScope.launch {
                signInService.onSignInResult(result)
            }
        }
    }

    fun signUpUser() {
        try {
            getAuthResults.launch(signInService.signInIntent)
        } catch (e: Exception) {
            Log.d("signin", e.message.toString())
            //Timber.tag("LAUNCH_BT_ENABLE").e(e)
        }
    }

    suspend fun signOut() {
        signInService.signOut(activity as ComponentActivity)
    }

}
