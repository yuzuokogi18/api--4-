package com.equipxpress.application.usecases.roles

import com.equipxpress.domain.models.Role
import com.equipxpress.domain.repositories.RoleRepository

class UpdateRoleUseCase(private val roleRepository: RoleRepository) {
    suspend operator fun invoke(id: Int, role: Role): Result<Role> {
        return try {
            if (role.nombre.isBlank()) {
                return Result.failure(Exception("El nombre es requerido"))
            }
            val updated = roleRepository.update(id, role)
                ?: return Result.failure(Exception("Role no encontrado"))
            Result.success(updated)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}