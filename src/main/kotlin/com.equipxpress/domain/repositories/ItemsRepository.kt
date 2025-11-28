package com.equipxpress.domain.repositories

import com.equipxpress.domain.models.Item

interface ItemsRepository {
    suspend fun getAll(): List<Item>
    suspend fun getById(id: Int): Item?   // ✔ ESTA ES LA FIRMA CORRECTA
}
