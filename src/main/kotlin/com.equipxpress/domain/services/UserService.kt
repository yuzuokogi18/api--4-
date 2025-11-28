package com.equipxpress.domain.services

import com.equipxpress.domain.models.User
import com.equipxpress.domain.repositories.UserRepository

class UserService(private val repo: UserRepository) {
    suspend fun createUser(
        nombre: String,
        correo: String,
        password: String,
        rol: String
    ): User {
        val user = User(
            nombre = nombre,
            correo = correo,
            password = password,
            rol = rol
        )
        return repo.create(user)
    }

    suspend fun getUserById(id: Int): User? {
        return repo.findById(id)
    }

    suspend fun login(correo: String, password: String): User? {
        return repo.findByCorreo(correo)
    }
}
