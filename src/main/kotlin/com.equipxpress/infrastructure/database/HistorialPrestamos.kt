package com.equipxpress.infrastructure.database

import org.jetbrains.exposed.sql.Table

object HistorialPrestamosTable : Table("historial_prestamos") {
    val id = integer("id").autoIncrement()
    val requestId = integer("request_id")
    val fechaEntrega = varchar("fecha_entrega", 50).nullable()
    val fechaDevolucion = varchar("fecha_devolucion", 50).nullable()
    val estadoPrestamo = varchar("estado_prestamo", 50)
    val observaciones = text("observaciones").nullable()
    
    override val primaryKey = PrimaryKey(id)
}