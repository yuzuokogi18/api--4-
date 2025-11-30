package com.equipxpress.application.usecases.notifications

import com.equipxpress.domain.models.Notification
import com.equipxpress.domain.repositories.NotificationRepository


class MarkNotificationAsReadUseCase(private val notificationRepository: NotificationRepository) {
    suspend operator fun invoke(id: Int): Result<Boolean> {
        return try {
            val marked = notificationRepository.markAsRead(id)
            if (marked) {
                Result.success(true)
            } else {
                Result.failure(Exception("Notificación no encontrada"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
