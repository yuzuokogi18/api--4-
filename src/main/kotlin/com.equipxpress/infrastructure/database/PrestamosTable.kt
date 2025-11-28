package com.equipxpress.infrastructure.database

import org.jetbrains.exposed.sql.Table


object PrestamosTable : Table("historial_prestamos") {
    val id = integer("id").autoIncrement()
    val requestId = integer("request_id")
    val fechaEntrega = varchar("fecha_entrega", 255).nullable()
    val fechaDevolucion = varchar("fecha_devolucion", 255).nullable()
    val estadoPrestamo = varchar("estado_prestamo", 20)
    val observaciones = text("observaciones").nullable()
    override val primaryKey = PrimaryKey(id)
}
