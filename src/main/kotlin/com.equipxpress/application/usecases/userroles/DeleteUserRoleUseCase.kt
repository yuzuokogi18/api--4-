package com.equipxpress.application.usecases.userroles

import com.equipxpress.domain.models.UserRole
import com.equipxpress.domain.repositories.UserRoleRepository


class DeleteUserRoleUseCase(private val userRoleRepository: UserRoleRepository) {
    suspend operator fun invoke(id: Int): Result<Boolean> {
        return try {
            val deleted = userRoleRepository.delete(id)
            if (deleted) {
                Result.success(true)
            } else {
                Result.failure(Exception("UserRole no encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}