package com.equipxpress.infrastructure.repositoriesImpl

import com.equipxpress.domain.models.Item
import com.equipxpress.domain.repositories.ItemsRepository
import com.equipxpress.infrastructure.database.ItemsTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.time.Instant

class ItemsRepositoryImpl : ItemsRepository {
    
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction { block() }
    
    override suspend fun getAll(): List<Item> = dbQuery {
        ItemsTable.selectAll()
            .map(::resultRowToItem)
    }
    
    override suspend fun getById(id: Int): Item? = dbQuery {
        ItemsTable.select { ItemsTable.id eq id }
            .map(::resultRowToItem)
            .singleOrNull()
    }
    
    override suspend fun create(item: Item): Item = dbQuery {
        val now = Instant.now()
        val insertStatement = ItemsTable.insert {
            it[nombre] = item.nombre
            it[descripcion] = item.descripcion
            it[imagen] = item.imagen
            it[categoriaId] = item.categoriaId
            it[estado] = item.estado
            it[stock] = item.stock
            it[createdAt] = now
            it[updatedAt] = now
        }
        val resultRow = insertStatement.resultedValues?.first()
        resultRowToItem(resultRow!!)
    }
    
    override suspend fun update(id: Int, item: Item): Item? = dbQuery {
        ItemsTable.update({ ItemsTable.id eq id }) {
            it[nombre] = item.nombre
            it[descripcion] = item.descripcion
            it[imagen] = item.imagen
            it[categoriaId] = item.categoriaId
            it[estado] = item.estado
            it[stock] = item.stock
            it[updatedAt] = Instant.now()
        }
        getById(id)
    }
    
    override suspend fun delete(id: Int): Boolean = dbQuery {
        ItemsTable.deleteWhere { ItemsTable.id eq id } > 0
    }
    
    private fun resultRowToItem(row: ResultRow): Item {
        return Item(
            id = row[ItemsTable.id],
            nombre = row[ItemsTable.nombre],
            descripcion = row[ItemsTable.descripcion],
            imagen = row[ItemsTable.imagen],
            categoriaId = row[ItemsTable.categoriaId],
            estado = row[ItemsTable.estado],
            stock = row[ItemsTable.stock],
            createdAt = row[ItemsTable.createdAt].toString(),
            updatedAt = row[ItemsTable.updatedAt].toString()
        )
    }
}