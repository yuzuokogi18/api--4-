package com.equipxpress.infrastructure.repositoriesImpl

import com.equipxpress.domain.models.HistorialPrestamo
import com.equipxpress.domain.repositories.PrestamosRepository
import com.equipxpress.infrastructure.database.HistorialPrestamosTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class PrestamosRepositoryImpl : PrestamosRepository {
    
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction { block() }
    
    override suspend fun registrarPrestamo(prestamo: HistorialPrestamo): HistorialPrestamo = dbQuery {
        val insertStatement = HistorialPrestamosTable.insert {
            it[requestId] = prestamo.requestId
            it[fechaEntrega] = prestamo.fechaEntrega
            it[fechaDevolucion] = prestamo.fechaDevolucion
            it[estadoPrestamo] = prestamo.estadoPrestamo
            it[observaciones] = prestamo.observaciones
        }
        val resultRow = insertStatement.resultedValues?.first()
        resultRowToHistorialPrestamo(resultRow!!)
    }
    
    override suspend fun getAll(): List<HistorialPrestamo> = dbQuery {
        HistorialPrestamosTable.selectAll()
            .map(::resultRowToHistorialPrestamo)
    }
    
    override suspend fun getById(id: Int): HistorialPrestamo? = dbQuery {
        HistorialPrestamosTable.select { HistorialPrestamosTable.id eq id }
            .map(::resultRowToHistorialPrestamo)
            .singleOrNull()
    }
    
    override suspend fun getByUser(userId: Int): List<HistorialPrestamo> = dbQuery {
        // Nota: HistorialPrestamosTable no tiene userId directamente
        // Si necesitas esta funcionalidad, debes agregar la columna userId a la tabla
        emptyList()
    }
    
    override suspend fun update(id: Int, prestamo: HistorialPrestamo): HistorialPrestamo? = dbQuery {
        HistorialPrestamosTable.update({ HistorialPrestamosTable.id eq id }) {
            it[requestId] = prestamo.requestId
            it[fechaEntrega] = prestamo.fechaEntrega
            it[fechaDevolucion] = prestamo.fechaDevolucion
            it[estadoPrestamo] = prestamo.estadoPrestamo
            it[observaciones] = prestamo.observaciones
        }
        getById(id)
    }
    
    override suspend fun delete(id: Int): Boolean = dbQuery {
        HistorialPrestamosTable.deleteWhere { HistorialPrestamosTable.id eq id } > 0
    }
    
    private fun resultRowToHistorialPrestamo(row: ResultRow): HistorialPrestamo {
        return HistorialPrestamo(
            id = row[HistorialPrestamosTable.id],
            requestId = row[HistorialPrestamosTable.requestId],
            fechaEntrega = row[HistorialPrestamosTable.fechaEntrega],
            fechaDevolucion = row[HistorialPrestamosTable.fechaDevolucion],
            estadoPrestamo = row[HistorialPrestamosTable.estadoPrestamo],
            observaciones = row[HistorialPrestamosTable.observaciones]
        )
    }
}