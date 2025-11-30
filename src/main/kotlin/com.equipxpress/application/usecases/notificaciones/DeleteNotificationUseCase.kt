package com.equipxpress.application.usecases.notifications

import com.equipxpress.domain.models.Notification
import com.equipxpress.domain.repositories.NotificationRepository

class DeleteNotificationUseCase(private val notificationRepository: NotificationRepository) {
    suspend operator fun invoke(id: Int): Result<Boolean> {
        return try {
            val deleted = notificationRepository.delete(id)
            if (deleted) {
                Result.success(true)
            } else {
                Result.failure(Exception("Notificación no encontrada"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}