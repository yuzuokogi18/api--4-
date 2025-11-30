// CreatePrestamoUseCase.kt
package com.equipxpress.application.usecases.prestamos

import com.equipxpress.domain.models.HistorialPrestamo
import com.equipxpress.domain.repositories.PrestamosRepository

class CreatePrestamoUseCase(
    private val prestamosRepository: PrestamosRepository
) {
    suspend operator fun invoke(prestamo: HistorialPrestamo): Result<HistorialPrestamo> {
        return try {
            // Validaciones
            if (prestamo.estadoPrestamo.isBlank()) {
                return Result.failure(Exception("El estado es requerido"))
            }
            
            val createdPrestamo = prestamosRepository.registrarPrestamo(prestamo)
            Result.success(createdPrestamo)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}