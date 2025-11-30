package com.equipxpress.infrastructure.repositoriesImpl

import com.equipxpress.domain.models.Request
import com.equipxpress.domain.repositories.RequestsRepository
import com.equipxpress.infrastructure.database.RequestsTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.time.Instant

class RequestsRepositoryImpl : RequestsRepository {
    
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction { block() }
    
    override suspend fun create(request: Request): Request = dbQuery {
        val insertStatement = RequestsTable.insert {
            it[userId] = request.userId
            it[itemId] = request.itemId
            it[estado] = request.estado
            it[fechaSolicitud] = Instant.now()
        }
        val resultRow = insertStatement.resultedValues?.first()
        resultRowToRequest(resultRow!!)
    }
    
    override suspend fun getByUser(userId: Int): List<Request> = dbQuery {
        RequestsTable.select { RequestsTable.userId eq userId }
            .map(::resultRowToRequest)
    }
    
    override suspend fun getAll(): List<Request> = dbQuery {
        RequestsTable.selectAll()
            .map(::resultRowToRequest)
    }
    
    override suspend fun getById(id: Int): Request? = dbQuery {
        RequestsTable.select { RequestsTable.id eq id }
            .map(::resultRowToRequest)
            .singleOrNull()
    }
    
    override suspend fun update(id: Int, request: Request): Request? = dbQuery {
        RequestsTable.update({ RequestsTable.id eq id }) {
            it[userId] = request.userId
            it[itemId] = request.itemId
            it[estado] = request.estado
            it[fechaRespuesta] = Instant.now()
        }
        getById(id)
    }
    
    override suspend fun delete(id: Int): Boolean = dbQuery {
        RequestsTable.deleteWhere { RequestsTable.id eq id } > 0
    }
    
    private fun resultRowToRequest(row: ResultRow): Request {
        return Request(
            id = row[RequestsTable.id],
            userId = row[RequestsTable.userId],
            itemId = row[RequestsTable.itemId],
            estado = row[RequestsTable.estado],
            fechaSolicitud = row[RequestsTable.fechaSolicitud].toString(),
            fechaRespuesta = row[RequestsTable.fechaRespuesta]?.toString()
        )
    }
}