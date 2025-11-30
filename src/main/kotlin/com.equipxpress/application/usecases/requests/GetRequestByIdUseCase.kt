package com.equipxpress.application.usecases.requests

import com.equipxpress.domain.models.Request
import com.equipxpress.domain.repositories.RequestsRepository

class GetRequestByIdUseCase(private val requestsRepository: RequestsRepository) {
    suspend operator fun invoke(id: Int): Result<Request> {
        return try {
            val request = requestsRepository.getById(id)
                ?: return Result.failure(Exception("Solicitud no encontrada"))
            Result.success(request)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}