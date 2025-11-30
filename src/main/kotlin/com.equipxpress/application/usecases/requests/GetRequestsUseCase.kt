package com.equipxpress.application.usecases.requests

import com.equipxpress.domain.models.Request
import com.equipxpress.domain.repositories.RequestsRepository

class GetRequestsUseCase(private val requestsRepository: RequestsRepository) {
    suspend fun getByUser(userId: Int): Result<List<Request>> {
        return try {
            val requests = requestsRepository.getByUser(userId)
            Result.success(requests)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}