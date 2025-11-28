package com.equipxpress.infrastructure.database

import org.jetbrains.exposed.sql.Table

object RequestsTable : Table("requests") {
    val id = integer("id").autoIncrement()
    val userId = integer("user_id")
    val itemId = integer("item_id")
    val estado = varchar("estado", 20)
    override val primaryKey = PrimaryKey(id)
}
