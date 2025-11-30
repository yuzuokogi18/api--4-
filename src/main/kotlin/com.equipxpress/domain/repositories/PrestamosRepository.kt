package com.equipxpress.domain.repositories

import com.equipxpress.domain.models.HistorialPrestamo

interface PrestamosRepository {
    suspend fun registrarPrestamo(prestamo: HistorialPrestamo): HistorialPrestamo
    suspend fun getAll(): List<HistorialPrestamo>
    suspend fun getById(id: Int): HistorialPrestamo?
    suspend fun getByUser(userId: Int): List<HistorialPrestamo>
    suspend fun update(id: Int, prestamo: HistorialPrestamo): HistorialPrestamo?
    suspend fun delete(id: Int): Boolean
}