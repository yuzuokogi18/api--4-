package com.equipxpress.infrastructure.database

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp
import java.time.Instant

object ItemsTable : Table("items") {
    val id = integer("id").autoIncrement()
    val nombre = varchar("nombre", 120)
    val descripcion = text("descripcion").nullable()
    val imagen = varchar("imagen", 255).nullable()
    val categoriaId = integer("categoria_id").references(CategoriasTable.id).nullable()
    val estado = varchar("estado", 20)
    val stock = integer("stock").default(1)
    val createdAt = timestamp("created_at").default(Instant.now())
    val updatedAt = timestamp("updated_at").default(Instant.now())
    
    override val primaryKey = PrimaryKey(id)
}