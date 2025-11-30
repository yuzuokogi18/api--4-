package com.equipxpress.application.usecases.items

import com.equipxpress.domain.models.Item
import com.equipxpress.domain.repositories.ItemsRepository

class CreateItemUseCase(private val itemsRepository: ItemsRepository) {
    suspend operator fun invoke(item: Item): Result<Item> {
        return try {
            if (item.nombre.isBlank()) {
                return Result.failure(Exception("El nombre es requerido"))
            }
            
            if (item.stock < 0) {
                return Result.failure(Exception("El stock no puede ser negativo"))
            }
            
            val createdItem = itemsRepository.create(item)
            Result.success(createdItem)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}