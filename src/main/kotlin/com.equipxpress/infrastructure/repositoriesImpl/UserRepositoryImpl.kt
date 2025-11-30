package com.equipxpress.infrastructure.repositoriesImpl

import com.equipxpress.domain.models.User
import com.equipxpress.domain.repositories.UserRepository
import com.equipxpress.infrastructure.database.UserTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.time.Instant

class UserRepositoryImpl : UserRepository {
    
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction { block() }
    
    override suspend fun create(user: User): User = dbQuery {
        val now = Instant.now()
        val insertStatement = UserTable.insert {
            it[nombre] = user.nombre
            it[correo] = user.correo
            it[password] = user.password
            it[rol] = user.rol
            it[createdAt] = now
            it[updatedAt] = now
        }
        val resultRow = insertStatement.resultedValues?.first()
        resultRowToUser(resultRow!!)
    }
    
    override suspend fun findByCorreo(correo: String): User? = dbQuery {
        UserTable.select { UserTable.correo eq correo }
            .map(::resultRowToUser)
            .singleOrNull()
    }
    
    override suspend fun findById(id: Int): User? = dbQuery {
        UserTable.select { UserTable.id eq id }
            .map(::resultRowToUser)
            .singleOrNull()
    }
    
    override suspend fun getAll(): List<User> = dbQuery {
        UserTable.selectAll()
            .map(::resultRowToUser)
    }
    
    override suspend fun update(id: Int, user: User): User? = dbQuery {
        UserTable.update({ UserTable.id eq id }) {
            it[nombre] = user.nombre
            it[correo] = user.correo
            it[password] = user.password
            it[rol] = user.rol
            it[updatedAt] = Instant.now()
        }
        findById(id)
    }
    
    override suspend fun delete(id: Int): Boolean = dbQuery {
        UserTable.deleteWhere { UserTable.id eq id } > 0
    }
    
    private fun resultRowToUser(row: ResultRow): User {
        return User(
            id = row[UserTable.id],
            nombre = row[UserTable.nombre],
            correo = row[UserTable.correo],
            password = row[UserTable.password],
            rol = row[UserTable.rol],
            createdAt = row[UserTable.createdAt].toString(),
            updatedAt = row[UserTable.updatedAt].toString()
        )
    }
}