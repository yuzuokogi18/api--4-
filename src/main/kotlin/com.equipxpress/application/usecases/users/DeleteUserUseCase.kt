package com.equipxpress.application.usecases.users

import com.equipxpress.domain.repositories.UserRepository

class DeleteUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(id: Int): Result<Boolean> {
        return try {
            val deleted = userRepository.delete(id)
            if (deleted) {
                Result.success(true)
            } else {
                Result.failure(Exception("Usuario no encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}