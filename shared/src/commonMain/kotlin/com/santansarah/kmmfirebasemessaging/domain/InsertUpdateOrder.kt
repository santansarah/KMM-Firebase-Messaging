package com.santansarah.kmmfirebasemessaging.domain

import com.santansarah.kmmfirebasemessaging.data.local.OrderRepository
import com.santansarah.kmmfirebasemessaging.data.local.UserRepository
import com.santansarah.kmmfirebasemessaging.utils.ServiceResult

class InsertUpdateOrder(
    private val userRepository: UserRepository,
    private val orderRepository: OrderRepository,
    private val notificationService: INotificationService
) {

    suspend operator fun invoke(
        orderId: String,
        productId: Int,
        isShipped: Boolean
    ): ServiceResult {

        val uid = userRepository.currentUser?.uid ?: "0"

            orderRepository.upsertOrder(uid = uid,
                orderId = orderId,
                productId = productId,
                isShipped = isShipped
            )

        notificationService.showOrderPlacedNotification(orderId)

        return ServiceResult.Success(true)

    }

}