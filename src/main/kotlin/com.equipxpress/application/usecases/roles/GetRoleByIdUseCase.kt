package com.equipxpress.application.usecases.roles

import com.equipxpress.domain.models.Role
import com.equipxpress.domain.repositories.RoleRepository


class GetRoleByIdUseCase(private val roleRepository: RoleRepository) {
    suspend operator fun invoke(id: Int): Result<Role> {
        return try {
            val role = roleRepository.getById(id)
                ?: return Result.failure(Exception("Role no encontrado"))
            Result.success(role)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}


