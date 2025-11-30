package com.equipxpress.application.usecases.userroles

import com.equipxpress.domain.models.UserRole
import com.equipxpress.domain.repositories.UserRoleRepository

class RemoveRoleFromUserUseCase(private val userRoleRepository: UserRoleRepository) {
    suspend operator fun invoke(userId: Int, roleId: Int): Result<Boolean> {
        return try {
            val deleted = userRoleRepository.deleteByUserAndRole(userId, roleId)
            if (deleted) {
                Result.success(true)
            } else {
                Result.failure(Exception("Relación no encontrada"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
