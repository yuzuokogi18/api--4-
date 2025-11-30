package com.equipxpress.application.usecases.notifications

import com.equipxpress.domain.models.Notification
import com.equipxpress.domain.repositories.NotificationRepository


class GetAllNotificationsUseCase(private val notificationRepository: NotificationRepository) {
    suspend operator fun invoke(): Result<List<Notification>> {
        return try {
            val notifications = notificationRepository.getAll()
            Result.success(notifications)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
