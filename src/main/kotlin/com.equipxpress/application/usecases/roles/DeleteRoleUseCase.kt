package com.equipxpress.application.usecases.roles

import com.equipxpress.domain.models.Role
import com.equipxpress.domain.repositories.RoleRepository

class DeleteRoleUseCase(private val roleRepository: RoleRepository) {
    suspend operator fun invoke(id: Int): Result<Boolean> {
        return try {
            val deleted = roleRepository.delete(id)
            if (deleted) {
                Result.success(true)
            } else {
                Result.failure(Exception("Role no encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}