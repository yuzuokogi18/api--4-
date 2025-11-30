package com.equipxpress.application.usecases.users

import com.equipxpress.domain.models.User
import com.equipxpress.domain.repositories.UserRepository
import com.equipxpress.utils.Hashing

class UpdateUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(id: Int, nombre: String, correo: String, password: String?, rol: String): Result<User> {
        return try {
            val existingUser = userRepository.findById(id)
                ?: return Result.failure(Exception("Usuario no encontrado"))
            
            val hashedPassword = if (password != null && password.isNotBlank()) {
                Hashing.hash(password)
            } else {
                existingUser.password
            }
            
            val updatedUser = User(
                id = id,
                nombre = nombre,
                correo = correo,
                password = hashedPassword,
                rol = rol
            )
            
            val result = userRepository.update(id, updatedUser)
                ?: return Result.failure(Exception("Error al actualizar usuario"))
            
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}