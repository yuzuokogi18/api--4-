package com.equipxpress.infrastructure.repositoriesImpl

import com.equipxpress.domain.models.HistorialPrestamo
import com.equipxpress.domain.repositories.PrestamosRepository
import com.equipxpress.infrastructure.database.PrestamosTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class PrestamosRepositoryImpl : PrestamosRepository {

    override suspend fun registrarPrestamo(historial: HistorialPrestamo): HistorialPrestamo {
        transaction {
            PrestamosTable.insert {
                it[PrestamosTable.requestId] = historial.requestId
                it[PrestamosTable.fechaEntrega] = historial.fechaEntrega
                it[PrestamosTable.fechaDevolucion] = historial.fechaDevolucion
                it[PrestamosTable.estadoPrestamo] = historial.estadoPrestamo
                it[PrestamosTable.observaciones] = historial.observaciones
            }
        }
        return historial
    }
}
