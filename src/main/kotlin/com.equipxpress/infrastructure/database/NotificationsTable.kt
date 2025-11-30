package com.equipxpress.infrastructure.database

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp
import java.time.Instant

object NotificationsTable : Table("notifications") {
    val id = integer("id").autoIncrement()
    val userId = integer("user_id").references(UserTable.id)
    val requestId = integer("request_id").references(RequestsTable.id).nullable()
    val mensaje = text("mensaje")
    val leida = bool("leida").default(false)
    val createdAt = timestamp("created_at").default(Instant.now())
    
    override val primaryKey = PrimaryKey(id)
}