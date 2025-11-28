package com.equipxpress.domain.repositories

import com.equipxpress.domain.models.User

interface UserRepository {
    suspend fun create(user: User): User
    suspend fun findByCorreo(correo: String): User?
    suspend fun findById(id: Int): User?
}
