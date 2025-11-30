package com.equipxpress.infrastructure.database

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp
import java.time.Instant

object UserTable : Table("users") {
    val id = integer("id").autoIncrement()
    val nombre = varchar("nombre", 100)
    val correo = varchar("correo", 120).uniqueIndex()
    val password = varchar("password", 255)
    val rol = varchar("rol", 50).default("USUARIO")  // Temporal - mejor usar user_roles
    val createdAt = timestamp("created_at").default(Instant.now())
    val updatedAt = timestamp("updated_at").default(Instant.now())
    
    override val primaryKey = PrimaryKey(id)
}