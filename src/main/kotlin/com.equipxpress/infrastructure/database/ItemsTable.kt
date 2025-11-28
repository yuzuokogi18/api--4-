package com.equipxpress.infrastructure.database

import org.jetbrains.exposed.sql.Table

object ItemsTable : Table("items") {
    val id = integer("id").autoIncrement()
    val nombre = varchar("nombre", 255)
    val descripcion = varchar("descripcion", 500).nullable()
    val imagen = varchar("imagen", 500).nullable()
    val categoriaId = integer("categoria_id").nullable()
    val estado = varchar("estado", 50)
    val stock = integer("stock")

    override val primaryKey = PrimaryKey(id)
}
