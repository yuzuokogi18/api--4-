package com.equipxpress.domain.repositories

import com.equipxpress.domain.models.Item

interface ItemsRepository {
    suspend fun getAll(): List<Item>
    suspend fun getById(id: Int): Item?
    suspend fun create(item: Item): Item
    suspend fun update(id: Int, item: Item): Item?
    suspend fun delete(id: Int): Boolean
}