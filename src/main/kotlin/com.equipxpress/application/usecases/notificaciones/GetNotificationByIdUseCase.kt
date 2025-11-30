package com.equipxpress.application.usecases.notifications

import com.equipxpress.domain.models.Notification
import com.equipxpress.domain.repositories.NotificationRepository

class GetNotificationByIdUseCase(private val notificationRepository: NotificationRepository) {
    suspend operator fun invoke(id: Int): Result<Notification> {
        return try {
            val notification = notificationRepository.getById(id)
                ?: return Result.failure(Exception("Notificación no encontrada"))
            Result.success(notification)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}