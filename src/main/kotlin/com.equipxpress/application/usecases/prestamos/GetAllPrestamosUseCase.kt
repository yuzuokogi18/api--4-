package com.equipxpress.application.usecases.prestamos

import com.equipxpress.domain.models.HistorialPrestamo
import com.equipxpress.domain.repositories.PrestamosRepository

class GetAllPrestamosUseCase(private val prestamosRepository: PrestamosRepository) {
    suspend operator fun invoke(): Result<List<HistorialPrestamo>> {
        return try {
            val prestamos = prestamosRepository.getAll()
            Result.success(prestamos)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}