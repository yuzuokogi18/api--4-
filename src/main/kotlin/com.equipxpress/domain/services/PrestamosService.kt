package com.equipxpress.domain.services

import com.equipxpress.domain.models.HistorialPrestamo
import com.equipxpress.domain.repositories.PrestamosRepository

class PrestamosService(private val repo: PrestamosRepository) {
    suspend fun registrarPrestamo(userId: Int, itemId: Int): HistorialPrestamo {
        val historial = HistorialPrestamo(
            requestId = userId,
            fechaEntrega = null,
            fechaDevolucion = null,
            estadoPrestamo = "activo",
            observaciones = null
        )
        return repo.registrarPrestamo(historial)
    }
    
    suspend fun registrar(data: HistorialPrestamo) = repo.registrarPrestamo(data)
}
