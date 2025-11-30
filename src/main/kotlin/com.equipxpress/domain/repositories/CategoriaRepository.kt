package com.equipxpress.domain.repositories

import com.equipxpress.domain.models.Categoria

interface CategoriaRepository {
    suspend fun getAll(): List<Categoria>
    suspend fun getById(id: Int): Categoria?
    suspend fun create(categoria: Categoria): Categoria
    suspend fun update(id: Int, categoria: Categoria): Categoria?
    suspend fun delete(id: Int): Boolean
}