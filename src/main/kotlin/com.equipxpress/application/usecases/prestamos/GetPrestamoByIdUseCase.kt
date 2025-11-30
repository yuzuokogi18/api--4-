package com.equipxpress.application.usecases.prestamos

import com.equipxpress.domain.models.HistorialPrestamo
import com.equipxpress.domain.repositories.PrestamosRepository

class GetPrestamoByIdUseCase(private val prestamosRepository: PrestamosRepository) {
    suspend operator fun invoke(id: Int): Result<HistorialPrestamo> {
        return try {
            val prestamo = prestamosRepository.getById(id)
                ?: return Result.failure(Exception("Préstamo no encontrado"))
            Result.success(prestamo)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}