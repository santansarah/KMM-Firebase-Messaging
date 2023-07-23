package com.santansarah.kmmfirebasemessaging.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent


data class AppPreferences(
    val lastOnboardingScreen: Int = 0,
    val isOnboardingComplete: Boolean = false,
    val userId: Int = 0,
    val isSignedOut: Boolean
)

class AppPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) : KoinComponent {

    private val logger = Logger.withTag("UserPreferencesManager")

    private object PreferencesKeys {
        val LAST_ONBOARDING_SCREEN = intPreferencesKey("last_onboarding")
        val USER_ID = intPreferencesKey("userId")
        val IS_SIGNED_OUT = booleanPreferencesKey("isSignedOut")
    }

    suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }

    /**
     * Use this if you don't want to observe a flow.
     */
    suspend fun fetchInitialPreferences() =
        mapAppPreferences(dataStore.data.first().toPreferences())

    /**
     * Get the user preferences flow. When it's collected, keys are mapped to the
     * [UserPreferences] data class.
     */
    val userPreferencesFlow: Flow<AppPreferences> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                logger.d { "Error reading preferences: $exception" }
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            mapAppPreferences(preferences)
        }

    /**
     * Sets the last onboarding screen that was viewed (on button click).
     */
    suspend fun setLastOnboardingScreen(viewedScreen: Int) {
        // updateData handles data transactionally, ensuring that if the key is updated at the same
        // time from another thread, we won't have conflicts
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.LAST_ONBOARDING_SCREEN] = viewedScreen
        }
    }

    /**
     * Sets the userId that we get from the Ktor API (on button click).
     */
    suspend fun setUserId(userId: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_ID] = userId
        }
    }

    suspend fun isSignedOut(isSignedOut: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_SIGNED_OUT] = isSignedOut
        }
    }

    /**
     * Get the preferences key, then map it to the data class.
     */
    private fun mapAppPreferences(preferences: Preferences): AppPreferences {
        val lastScreen = preferences[PreferencesKeys.LAST_ONBOARDING_SCREEN] ?: 0
        Logger.d { "lastScreen: $lastScreen" }
        val isOnBoardingComplete: Boolean = (lastScreen >= 1)
        val userId = preferences[PreferencesKeys.USER_ID] ?: 0
        val isSignedOut = preferences[PreferencesKeys.IS_SIGNED_OUT] ?: false

        return AppPreferences(lastScreen, isOnBoardingComplete, userId, isSignedOut)
    }
}