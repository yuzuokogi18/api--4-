package com.equipxpress.infrastructure.repositoriesImpl

import com.equipxpress.domain.models.Categoria
import com.equipxpress.domain.repositories.CategoriaRepository
import com.equipxpress.infrastructure.database.CategoriasTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class CategoriaRepositoryImpl : CategoriaRepository {
    
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction { block() }
    
    override suspend fun getAll(): List<Categoria> = dbQuery {
        CategoriasTable.selectAll()
            .map(::resultRowToCategoria)
    }
    
    override suspend fun getById(id: Int): Categoria? = dbQuery {
        CategoriasTable.select { CategoriasTable.id eq id }
            .map(::resultRowToCategoria)
            .singleOrNull()
    }
    
    override suspend fun create(categoria: Categoria): Categoria = dbQuery {
        val insertStatement = CategoriasTable.insert {
            it[nombre] = categoria.nombre
        }
        val resultRow = insertStatement.resultedValues?.first()
        resultRowToCategoria(resultRow!!)
    }
    
    override suspend fun update(id: Int, categoria: Categoria): Categoria? = dbQuery {
        CategoriasTable.update({ CategoriasTable.id eq id }) {
            it[nombre] = categoria.nombre
        }
        getById(id)
    }
    
    override suspend fun delete(id: Int): Boolean = dbQuery {
        CategoriasTable.deleteWhere { CategoriasTable.id eq id } > 0
    }
    
    private fun resultRowToCategoria(row: ResultRow): Categoria {
        return Categoria(
            id = row[CategoriasTable.id],
            nombre = row[CategoriasTable.nombre]
        )
    }
}