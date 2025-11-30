package com.equipxpress.application.usecases.roles

import com.equipxpress.domain.models.Role
import com.equipxpress.domain.repositories.RoleRepository

class CreateRoleUseCase(private val roleRepository: RoleRepository) {
    suspend operator fun invoke(role: Role): Result<Role> {
        return try {
            if (role.nombre.isBlank()) {
                return Result.failure(Exception("El nombre es requerido"))
            }
            val created = roleRepository.create(role)
            Result.success(created)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}