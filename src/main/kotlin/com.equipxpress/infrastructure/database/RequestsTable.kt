package com.equipxpress.infrastructure.database

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp
import java.time.Instant

object RequestsTable : Table("requests") {
    val id = integer("id").autoIncrement()
    val userId = integer("user_id").references(UserTable.id)
    val itemId = integer("item_id").references(ItemsTable.id)
    val estado = varchar("estado", 20)
    val fechaSolicitud = timestamp("fecha_solicitud").default(Instant.now())
    val fechaRespuesta = timestamp("fecha_respuesta").nullable()
    
    override val primaryKey = PrimaryKey(id)
}