package com.equipxpress.domain.repositories

import com.equipxpress.domain.models.HistorialPrestamo

interface PrestamosRepository {
    suspend fun registrarPrestamo(data: HistorialPrestamo): HistorialPrestamo
}
