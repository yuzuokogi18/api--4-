package com.equipxpress.infrastructure.repositoriesImpl

import com.equipxpress.domain.models.Item
import com.equipxpress.domain.repositories.ItemsRepository
import com.equipxpress.infrastructure.database.ItemsTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ItemsRepositoryImpl : ItemsRepository {

    override suspend fun getAll(): List<Item> = transaction {
        ItemsTable.selectAll().map { row ->
            Item(
                id = row[ItemsTable.id],
                nombre = row[ItemsTable.nombre],
                descripcion = row[ItemsTable.descripcion],
                imagen = row[ItemsTable.imagen],
                categoriaId = row[ItemsTable.categoriaId],
                estado = row[ItemsTable.estado],
                stock = row[ItemsTable.stock]
            )
        }
    }

    override suspend fun getById(id: Int): Item? = transaction {
        ItemsTable
            .select { ItemsTable.id inList listOf(id) }   // 🔥 SIN eq y SIN errores
            .singleOrNull()
            ?.let { row ->
                Item(
                    id = row[ItemsTable.id],
                    nombre = row[ItemsTable.nombre],
                    descripcion = row[ItemsTable.descripcion],
                    imagen = row[ItemsTable.imagen],
                    categoriaId = row[ItemsTable.categoriaId],
                    estado = row[ItemsTable.estado],
                    stock = row[ItemsTable.stock]
                )
            }
    }
}
