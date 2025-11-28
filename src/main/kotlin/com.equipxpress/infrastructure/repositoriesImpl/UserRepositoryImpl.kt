package com.equipxpress.infrastructure.repositoriesImpl

import com.equipxpress.domain.models.User
import com.equipxpress.domain.repositories.UserRepository
import com.equipxpress.infrastructure.database.UserTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepositoryImpl : UserRepository {

    override fun createUser(user: User): User {
        transaction {
            UserTable.insert {
                it[nombre] = user.nombre
                it[correo] = user.correo
                it[password] = user.password
                it[rol] = user.rol
            }
        }
        return user
    }

    override fun findByEmail(correo: String): User? = transaction {
        UserTable.select { UserTable.correo eq correo }.singleOrNull()?.let {
            User(
                id = it[UserTable.id],
                nombre = it[UserTable.nombre],
                correo = it[UserTable.correo],
                password = it[UserTable.password],
                rol = it[UserTable.rol]
            )
        }
    }

    override fun findById(id: Int): User? = transaction {
        UserTable.select { UserTable.id eq id }.singleOrNull()?.let {
            User(
                id = it[UserTable.id],
                nombre = it[UserTable.nombre],
                correo = it[UserTable.correo],
                password = it[UserTable.password],
                rol = it[UserTable.rol]
            )
        }
    }
}
