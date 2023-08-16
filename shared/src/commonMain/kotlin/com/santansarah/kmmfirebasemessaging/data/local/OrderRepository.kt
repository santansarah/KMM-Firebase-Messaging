package com.santansarah.kmmfirebasemessaging.data.local

import co.touchlab.kermit.Logger
import dev.gitlive.firebase.firestore.FirebaseFirestore

class OrderRepository(
    private val db: FirebaseFirestore
) {

    suspend fun upsertOrder(
        uid: String,
        orderId: String,
        productId: Int,
        isShipped: Boolean = false
    ) {

        val order = hashMapOf(
            "uid" to uid,
            "productId" to productId,
            "isShipped" to isShipped
        )

        try {
            db.collection("orders").document(orderId)
                .set(order, merge = true)

        } catch (e: Exception) {
            Logger.d { e.message.toString() }
        }

    }


}