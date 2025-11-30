package com.equipxpress.infrastructure.repositoriesImpl

import com.equipxpress.domain.models.Notification
import com.equipxpress.domain.repositories.NotificationRepository
import com.equipxpress.infrastructure.database.NotificationsTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.time.Instant

class NotificationRepositoryImpl : NotificationRepository {
    
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction { block() }
    
    override suspend fun getAll(): List<Notification> = dbQuery {
        NotificationsTable.selectAll()
            .map(::resultRowToNotification)
    }
    
    override suspend fun getByUser(userId: Int): List<Notification> = dbQuery {
        NotificationsTable.select { NotificationsTable.userId eq userId }
            .map(::resultRowToNotification)
    }
    
    override suspend fun getById(id: Int): Notification? = dbQuery {
        NotificationsTable.select { NotificationsTable.id eq id }
            .map(::resultRowToNotification)
            .singleOrNull()
    }
    
    override suspend fun create(notification: Notification): Notification = dbQuery {
        val insertStatement = NotificationsTable.insert {
            it[userId] = notification.userId
            it[requestId] = notification.requestId
            it[mensaje] = notification.mensaje
            it[leida] = notification.leida
            it[createdAt] = Instant.now()
        }
        val resultRow = insertStatement.resultedValues?.first()
        resultRowToNotification(resultRow!!)
    }
    
    override suspend fun markAsRead(id: Int): Boolean = dbQuery {
        NotificationsTable.update({ NotificationsTable.id eq id }) {
            it[leida] = true
        } > 0
    }
    
    override suspend fun delete(id: Int): Boolean = dbQuery {
        NotificationsTable.deleteWhere { NotificationsTable.id eq id } > 0
    }
    
    private fun resultRowToNotification(row: ResultRow): Notification {
        return Notification(
            id = row[NotificationsTable.id],
            userId = row[NotificationsTable.userId],
            requestId = row[NotificationsTable.requestId],
            mensaje = row[NotificationsTable.mensaje],
            leida = row[NotificationsTable.leida],
            createdAt = row[NotificationsTable.createdAt].toString()
        )
    }
}