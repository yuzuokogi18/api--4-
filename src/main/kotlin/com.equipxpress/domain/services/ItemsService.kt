package com.equipxpress.domain.services

import com.equipxpress.domain.repositories.ItemsRepository

class ItemsService(private val repo: ItemsRepository) {
    suspend fun getAll() = repo.getAll()
    suspend fun getById(id: Int) = repo.getById(id)
}
