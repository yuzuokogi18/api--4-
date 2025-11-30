package com.equipxpress.infrastructure.repositoriesImpl

import com.equipxpress.domain.models.Role
import com.equipxpress.domain.repositories.RoleRepository
import com.equipxpress.infrastructure.database.RolesTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class RoleRepositoryImpl : RoleRepository {
    
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction { block() }
    
    override suspend fun getAll(): List<Role> = dbQuery {
        RolesTable.selectAll()
            .map(::resultRowToRole)
    }
    
    override suspend fun getById(id: Int): Role? = dbQuery {
        RolesTable.select { RolesTable.id eq id }
            .map(::resultRowToRole)
            .singleOrNull()
    }
    
    override suspend fun create(role: Role): Role = dbQuery {
        val insertStatement = RolesTable.insert {
            it[nombre] = role.nombre
        }
        val resultRow = insertStatement.resultedValues?.first()
        resultRowToRole(resultRow!!)
    }
    
    override suspend fun update(id: Int, role: Role): Role? = dbQuery {
        RolesTable.update({ RolesTable.id eq id }) {
            it[nombre] = role.nombre
        }
        getById(id)
    }
    
    override suspend fun delete(id: Int): Boolean = dbQuery {
        RolesTable.deleteWhere { RolesTable.id eq id } > 0
    }
    
    private fun resultRowToRole(row: ResultRow): Role {
        return Role(
            id = row[RolesTable.id],
            nombre = row[RolesTable.nombre]
        )
    }
}