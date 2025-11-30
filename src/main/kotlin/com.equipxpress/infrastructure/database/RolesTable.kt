package com.equipxpress.infrastructure.database

import org.jetbrains.exposed.sql.Table

object RolesTable : Table("roles") {
    val id = integer("id").autoIncrement()
    val nombre = varchar("nombre", 50).uniqueIndex()
    
    override val primaryKey = PrimaryKey(id)
}