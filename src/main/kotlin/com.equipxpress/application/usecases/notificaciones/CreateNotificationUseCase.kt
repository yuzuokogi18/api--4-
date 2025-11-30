package com.equipxpress.application.usecases.notifications

import com.equipxpress.domain.models.Notification
import com.equipxpress.domain.repositories.NotificationRepository

class CreateNotificationUseCase(private val notificationRepository: NotificationRepository) {
    suspend operator fun invoke(notification: Notification): Result<Notification> {
        return try {
            if (notification.mensaje.isBlank()) {
                return Result.failure(Exception("El mensaje es requerido"))
            }
            
            val createdNotification = notificationRepository.create(notification)
            Result.success(createdNotification)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
