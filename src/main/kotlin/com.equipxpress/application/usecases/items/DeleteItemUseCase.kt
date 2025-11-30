package com.equipxpress.application.usecases.items

import com.equipxpress.domain.repositories.ItemsRepository

class DeleteItemUseCase(private val itemsRepository: ItemsRepository) {
    suspend operator fun invoke(id: Int): Result<Boolean> {
        return try {
            val deleted = itemsRepository.delete(id)
            if (deleted) {
                Result.success(true)
            } else {
                Result.failure(Exception("Item no encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}