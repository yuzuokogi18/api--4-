package com.equipxpress.domain.repositories

import com.equipxpress.domain.models.Request

interface RequestsRepository {
    suspend fun create(request: Request): Request
    suspend fun getAll(): List<Request>
    suspend fun getByUser(userId: Int): List<Request>
    suspend fun getById(id: Int): Request?
    suspend fun update(id: Int, request: Request): Request?
    suspend fun delete(id: Int): Boolean
}