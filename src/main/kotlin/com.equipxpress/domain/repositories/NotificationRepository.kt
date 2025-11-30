package com.equipxpress.domain.repositories

import com.equipxpress.domain.models.Notification

interface NotificationRepository {
    suspend fun getAll(): List<Notification>
    suspend fun getByUser(userId: Int): List<Notification>
    suspend fun getById(id: Int): Notification?
    suspend fun create(notification: Notification): Notification
    suspend fun markAsRead(id: Int): Boolean
    suspend fun delete(id: Int): Boolean
}