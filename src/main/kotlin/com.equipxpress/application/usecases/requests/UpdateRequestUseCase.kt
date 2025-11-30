package com.equipxpress.application.usecases.requests

import com.equipxpress.domain.models.Request
import com.equipxpress.domain.repositories.RequestsRepository

class UpdateRequestUseCase(private val requestsRepository: RequestsRepository) {
    suspend operator fun invoke(id: Int, request: Request): Result<Request> {
        return try {
            val updatedRequest = requestsRepository.update(id, request)
                ?: return Result.failure(Exception("Solicitud no encontrada"))
            
            Result.success(updatedRequest)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}