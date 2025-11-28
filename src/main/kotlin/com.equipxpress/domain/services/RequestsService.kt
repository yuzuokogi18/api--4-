package com.equipxpress.domain.services

import com.equipxpress.domain.models.Request
import com.equipxpress.domain.repositories.RequestsRepository

class RequestsService(private val repo: RequestsRepository) {
    suspend fun create(req: Request) = repo.create(req)
    suspend fun getByUser(userId: Int) = repo.getByUser(userId)

    suspend fun createRequest(userId: Int, itemId: Int): Request {
        val request = Request(
            userId = userId,
            itemId = itemId,
            estado = "pendiente"
        )
        return repo.create(request)
    }
}
