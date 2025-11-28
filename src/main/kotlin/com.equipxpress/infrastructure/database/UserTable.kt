package com.equipxpress.infrastructure.database

import org.jetbrains.exposed.sql.Table



object UserTable : Table("users") {
    val id = integer("id").autoIncrement()
    val nombre = varchar("nombre", 100)
    val correo = varchar("correo", 120)
    val password = varchar("password", 255)
    override val primaryKey = PrimaryKey(id)
}
