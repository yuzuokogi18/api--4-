package com.equipxpress.application.usecases.requests

import com.equipxpress.domain.models.Request
import com.equipxpress.domain.repositories.RequestsRepository
import com.equipxpress.domain.repositories.ItemsRepository

class CreateRequestUseCase(
    private val requestsRepository: RequestsRepository,
    private val itemsRepository: ItemsRepository
) {
    suspend operator fun invoke(request: Request): Result<Request> {
        return try {
            // Validar que el item existe
            val item = itemsRepository.getById(request.itemId)
            if (item == null) {
                return Result.failure(Exception("El item no existe"))
            }
            
            // Crear solicitud
            val createdRequest = requestsRepository.create(request)
            Result.success(createdRequest)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}