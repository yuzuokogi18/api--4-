package com.equipxpress.application.usecases.users

import com.equipxpress.domain.models.User
import com.equipxpress.domain.repositories.UserRepository

class GetAllUsersUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(): Result<List<User>> {
        return try {
            val users = userRepository.getAll()
            Result.success(users)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}