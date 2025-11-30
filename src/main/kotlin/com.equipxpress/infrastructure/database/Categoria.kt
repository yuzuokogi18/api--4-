package com.equipxpress.infrastructure.database

import org.jetbrains.exposed.sql.Table

object CategoriasTable : Table("categorias") {
    val id = integer("id").autoIncrement()
    val nombre = varchar("nombre", 80).uniqueIndex()
    
    override val primaryKey = PrimaryKey(id)
}