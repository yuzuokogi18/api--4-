package com.equipxpress.application.usecases.prestamos

import com.equipxpress.domain.models.HistorialPrestamo
import com.equipxpress.domain.repositories.PrestamosRepository

class UpdatePrestamoUseCase(private val prestamosRepository: PrestamosRepository) {
    suspend operator fun invoke(id: Int, prestamo: HistorialPrestamo): Result<HistorialPrestamo> {
        return try {
            if (prestamo.estadoPrestamo.isBlank()) {
                return Result.failure(Exception("El estado es requerido"))
            }
            
            val updatedPrestamo = prestamosRepository.update(id, prestamo)
                ?: return Result.failure(Exception("Préstamo no encontrado"))
            
            Result.success(updatedPrestamo)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}