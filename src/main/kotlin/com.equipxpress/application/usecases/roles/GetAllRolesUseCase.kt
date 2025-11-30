package com.equipxpress.application.usecases.roles

import com.equipxpress.domain.models.Role
import com.equipxpress.domain.repositories.RoleRepository

class GetAllRolesUseCase(private val roleRepository: RoleRepository) {
    suspend operator fun invoke(): Result<List<Role>> {
        return try {
            val roles = roleRepository.getAll()
            Result.success(roles)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
