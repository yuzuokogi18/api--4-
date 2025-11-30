package com.equipxpress.application.usecases.prestamos

import com.equipxpress.domain.repositories.PrestamosRepository

class DeletePrestamoUseCase(private val prestamosRepository: PrestamosRepository) {
    suspend operator fun invoke(id: Int): Result<Boolean> {
        return try {
            val deleted = prestamosRepository.delete(id)
            if (deleted) {
                Result.success(true)
            } else {
                Result.failure(Exception("Préstamo no encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}