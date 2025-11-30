package com.equipxpress.application.usecases.notifications

import com.equipxpress.domain.models.Notification
import com.equipxpress.domain.repositories.NotificationRepository


class GetNotificationsByUserUseCase(private val notificationRepository: NotificationRepository) {
    suspend operator fun invoke(userId: Int): Result<List<Notification>> {
        return try {
            val notifications = notificationRepository.getByUser(userId)
            Result.success(notifications)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
