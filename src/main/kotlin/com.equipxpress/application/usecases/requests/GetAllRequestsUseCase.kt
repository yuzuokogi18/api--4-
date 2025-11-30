package com.equipxpress.application.usecases.requests

import com.equipxpress.domain.models.Request
import com.equipxpress.domain.repositories.RequestsRepository

class GetAllRequestsUseCase(private val requestsRepository: RequestsRepository) {
    suspend operator fun invoke(): Result<List<Request>> {
        return try {
            val requests = requestsRepository.getAll()
            Result.success(requests)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}