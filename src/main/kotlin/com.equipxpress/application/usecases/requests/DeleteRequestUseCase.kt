package com.equipxpress.application.usecases.requests

import com.equipxpress.domain.repositories.RequestsRepository

class DeleteRequestUseCase(private val requestsRepository: RequestsRepository) {
    suspend operator fun invoke(id: Int): Result<Boolean> {
        return try {
            val deleted = requestsRepository.delete(id)
            if (deleted) {
                Result.success(true)
            } else {
                Result.failure(Exception("Solicitud no encontrada"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}