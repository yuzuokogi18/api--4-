package com.equipxpress.infrastructure.repositoriesImpl

import com.equipxpress.domain.models.Request
import com.equipxpress.domain.repositories.RequestsRepository
import com.equipxpress.infrastructure.database.RequestsTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class RequestsRepositoryImpl : RequestsRepository {

    override fun createRequest(request: Request): Request {
        transaction {
            RequestsTable.insert {
                it[userId] = request.userId
                it[itemId] = request.itemId
                it[estado] = request.estado
            }
        }
        return request
    }

    override fun getRequestsByUser(userId: Int): List<Request> = transaction {
        RequestsTable.select { RequestsTable.userId eq userId }.map {
            Request(
                id = it[RequestsTable.id],
                userId = it[RequestsTable.userId],
                itemId = it[RequestsTable.itemId],
                estado = it[RequestsTable.estado]
            )
        }
    }
}
