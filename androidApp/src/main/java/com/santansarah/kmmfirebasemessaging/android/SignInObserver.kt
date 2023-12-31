package com.santansarah.kmmfirebasemessaging.android

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.santansarah.kmmfirebasemessaging.android.services.SignInService
import com.santansarah.kmmfirebasemessaging.data.local.UserRepository
import com.santansarah.kmmfirebasemessaging.utils.SignUpHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInObserver @Inject constructor(
    private val activity: Context,
    private val signInService: SignInService,
    private val userRepository: UserRepository
) : DefaultLifecycleObserver {

    private lateinit var getAuthResults: ActivityResultLauncher<Intent>

    private var _isUserSignedIn = MutableStateFlow(false)
    val isUserSignedIn = _isUserSignedIn.asStateFlow()

    override fun onCreate(owner: LifecycleOwner) {
        val registry = (activity as ComponentActivity).activityResultRegistry

        owner.lifecycleScope.launch {
            owner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                userRepository.isUserSignedIn.collect {
                    _isUserSignedIn.value = it
                }
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
