package com.equipxpress.domain.repositories

import com.equipxpress.domain.models.User

interface UserRepository {
    suspend fun create(user: User): User
    suspend fun findByCorreo(correo: String): User?
    suspend fun findById(id: Int): User?
    suspend fun getAll(): List<User>
    suspend fun update(id: Int, user: User): User?
    suspend fun delete(id: Int): Boolean
}