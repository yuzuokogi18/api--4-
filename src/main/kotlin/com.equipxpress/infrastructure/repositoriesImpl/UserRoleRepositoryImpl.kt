package com.equipxpress.infrastructure.repositoriesImpl

import com.equipxpress.domain.models.UserRole
import com.equipxpress.domain.repositories.UserRoleRepository
import com.equipxpress.infrastructure.database.UserRolesTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UserRoleRepositoryImpl : UserRoleRepository {
    
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction { block() }
    
    override suspend fun create(userRole: UserRole): UserRole = dbQuery {
        val insertStatement = UserRolesTable.insert {
            it[userId] = userRole.userId
            it[roleId] = userRole.roleId
        }
        val resultRow = insertStatement.resultedValues?.first()
        resultRowToUserRole(resultRow!!)
    }
    
    override suspend fun getAll(): List<UserRole> = dbQuery {
        UserRolesTable.selectAll()
            .map(::resultRowToUserRole)
    }
    
    override suspend fun getById(id: Int): UserRole? = dbQuery {
        UserRolesTable.select { UserRolesTable.id eq id }
            .map(::resultRowToUserRole)
            .singleOrNull()
    }
    
    override suspend fun getByUserId(userId: Int): List<UserRole> = dbQuery {
        UserRolesTable.select { UserRolesTable.userId eq userId }
            .map(::resultRowToUserRole)
    }
    
    override suspend fun getByRoleId(roleId: Int): List<UserRole> = dbQuery {
        UserRolesTable.select { UserRolesTable.roleId eq roleId }
            .map(::resultRowToUserRole)
    }
    
    override suspend fun delete(id: Int): Boolean = dbQuery {
        UserRolesTable.deleteWhere { UserRolesTable.id eq id } > 0
    }
    
    override suspend fun deleteByUserAndRole(userId: Int, roleId: Int): Boolean = dbQuery {
        UserRolesTable.deleteWhere { 
            (UserRolesTable.userId eq userId) and (UserRolesTable.roleId eq roleId)
        } > 0
    }
    
    private fun resultRowToUserRole(row: ResultRow): UserRole {
        return UserRole(
            id = row[UserRolesTable.id],
            userId = row[UserRolesTable.userId],
            roleId = row[UserRolesTable.roleId]
        )
    }
}