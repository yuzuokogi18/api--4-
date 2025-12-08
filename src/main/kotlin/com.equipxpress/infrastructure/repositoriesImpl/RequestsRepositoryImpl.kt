package com.equipxpress.infrastructure.repositoriesImpl

import com.equipxpress.domain.models.Request
import com.equipxpress.domain.repositories.RequestsRepository
import com.equipxpress.infrastructure.database.RequestsTable
import com.equipxpress.infrastructure.database.UserTable
import com.equipxpress.infrastructure.database.ItemsTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.time.Instant

class RequestsRepositoryImpl : RequestsRepository {

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction { block() }

    // CREATE: rellena userName e itemName automáticamente si vienen vacíos
    override suspend fun create(request: Request): Request = dbQuery {

        val userNameValue = request.userName?.takeIf { it.isNotBlank() }
            ?: UserTable.select { UserTable.id eq request.userId }
                .firstOrNull()?.get(UserTable.nombre)
                ?: "Usuario ${request.userId}"

        val itemNameValue = request.itemName?.takeIf { it.isNotBlank() }
            ?: ItemsTable.select { ItemsTable.id eq request.itemId }
                .firstOrNull()?.get(ItemsTable.nombre)
                ?: "Ítem ${request.itemId}"

        val insert = RequestsTable.insert {
            it[userId] = request.userId
            it[itemId] = request.itemId
            it[estado] = request.estado
            it[fechaSolicitud] = Instant.now()
            it[userName] = userNameValue
            it[itemName] = itemNameValue
        }

        resultRowToRequest(insert.resultedValues!!.first())
    }

    // GET ALL: Trae siempre el nombre actual del usuario e ítem usando JOIN
    override suspend fun getAll(): List<Request> = dbQuery {
        (RequestsTable innerJoin UserTable innerJoin ItemsTable)
            .selectAll()
            .map { row ->
                Request(
                    id = row[RequestsTable.id],
                    userId = row[RequestsTable.userId],
                    userName = row[UserTable.nombre],
                    itemId = row[RequestsTable.itemId],
                    itemName = row[ItemsTable.nombre],
                    estado = row[RequestsTable.estado],
                    fechaSolicitud = row[RequestsTable.fechaSolicitud].toString(),
                    fechaRespuesta = row[RequestsTable.fechaRespuesta]?.toString()
                )
            }
    }

    // GET BY USER: también usa JOIN para obtener los nombres actualizados
    override suspend fun getByUser(userId: Int): List<Request> = dbQuery {
        (RequestsTable innerJoin UserTable innerJoin ItemsTable)
            .select { RequestsTable.userId eq userId }
            .map { row ->
                Request(
                    id = row[RequestsTable.id],
                    userId = row[RequestsTable.userId],
                    userName = row[UserTable.nombre],
                    itemId = row[RequestsTable.itemId],
                    itemName = row[ItemsTable.nombre],
                    estado = row[RequestsTable.estado],
                    fechaSolicitud = row[RequestsTable.fechaSolicitud].toString(),
                    fechaRespuesta = row[RequestsTable.fechaRespuesta]?.toString()
                )
            }
    }

    // GET BY ID
    override suspend fun getById(id: Int): Request? = dbQuery {
        (RequestsTable innerJoin UserTable innerJoin ItemsTable)
            .select { RequestsTable.id eq id }
            .map { row ->
                Request(
                    id = row[RequestsTable.id],
                    userId = row[RequestsTable.userId],
                    userName = row[UserTable.nombre],
                    itemId = row[RequestsTable.itemId],
                    itemName = row[ItemsTable.nombre],
                    estado = row[RequestsTable.estado],
                    fechaSolicitud = row[RequestsTable.fechaSolicitud].toString(),
                    fechaRespuesta = row[RequestsTable.fechaRespuesta]?.toString()
                )
            }
            .singleOrNull()
    }

    // UPDATE
    override suspend fun update(id: Int, request: Request): Request? = dbQuery {
        RequestsTable.update({ RequestsTable.id eq id }) {
            it[estado] = request.estado
            it[fechaRespuesta] = Instant.now()
        }
        getById(id)
    }

    // DELETE
    override suspend fun delete(id: Int): Boolean = dbQuery {
        RequestsTable.deleteWhere { RequestsTable.id eq id } > 0
    }

    // Mapeo de ResultRow a Request para create()
    private fun resultRowToRequest(row: ResultRow): Request =
        Request(
            id = row[RequestsTable.id],
            userId = row[RequestsTable.userId],
            userName = row[RequestsTable.userName],
            itemId = row[RequestsTable.itemId],
            itemName = row[RequestsTable.itemName],
            estado = row[RequestsTable.estado],
            fechaSolicitud = row[RequestsTable.fechaSolicitud].toString(),
            fechaRespuesta = row[RequestsTable.fechaRespuesta]?.toString()
        )
}
