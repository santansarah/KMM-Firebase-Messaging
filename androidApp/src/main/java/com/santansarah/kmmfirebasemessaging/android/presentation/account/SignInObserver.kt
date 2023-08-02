package com.santansarah.kmmfirebasemessaging.android.presentation.account

import android.app.Activity.RESULT_OK
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.santansarah.kmmfirebasemessaging.android.services.SignInService
import com.santansarah.kmmfirebasemessaging.data.local.UserRepository
import com.santansarah.kmmfirebasemessaging.utils.SignUpHelper
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.math.sign

class SignInObserver @Inject constructor(
    private val activity: Context,
    private val signInService: SignInService
) : DefaultLifecycleObserver {

    private lateinit var getAuthResults: ActivityResultLauncher<Intent>

    override fun onCreate(owner: LifecycleOwner) {
        val registry = (activity as ComponentActivity).activityResultRegistry

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
        val success = signInService.signOut(activity)
    }

}
