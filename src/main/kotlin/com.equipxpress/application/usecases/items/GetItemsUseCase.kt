package com.equipxpress.application.usecases.items

import com.equipxpress.domain.models.Item
import com.equipxpress.domain.repositories.ItemsRepository

class GetItemsUseCase(private val itemsRepository: ItemsRepository) {
    suspend fun getAll(): Result<List<Item>> {
        return try {
            val items = itemsRepository.getAll()
            Result.success(items)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getById(id: Int): Result<Item> {
        return try {
            val item = itemsRepository.getById(id)
                ?: return Result.failure(Exception("Item no encontrado"))
            Result.success(item)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}