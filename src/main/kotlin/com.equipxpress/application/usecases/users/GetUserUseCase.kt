package com.equipxpress.application.usecases.users

import com.equipxpress.domain.models.User
import com.equipxpress.domain.repositories.UserRepository

class GetUserUseCase(private val userRepository: UserRepository) {
    suspend fun getById(id: Int): Result<User> {
        return try {
            val user = userRepository.findById(id)
                ?: return Result.failure(Exception("Usuario no encontrado"))
            
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}