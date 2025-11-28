package com.equipxpress.domain.repositories

import com.equipxpress.domain.models.Request

interface RequestsRepository {
    suspend fun create(req: Request): Request
    suspend fun getByUser(userId: Int): List<Request>
}
