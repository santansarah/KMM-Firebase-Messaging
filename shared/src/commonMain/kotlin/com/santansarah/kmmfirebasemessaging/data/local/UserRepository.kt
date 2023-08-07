package com.santansarah.kmmfirebasemessaging.data.local

import co.touchlab.kermit.Logger
import dev.gitlive.firebase.firestore.FirebaseFirestore

class UserRepository(
    private val db: FirebaseFirestore
) {

    suspend fun upsertUser(uid: String, token: String) {

        val user = hashMapOf(
            "uid" to uid,
            "token" to token
        )

        try {
            db.collection("users").document(uid)
                .set(user, merge = true)

        } catch (e: Exception) {
            Logger.d { e.message.toString() }
        }

    }

}