package com.equipxpress.application.usecases.userroles

import com.equipxpress.domain.models.UserRole
import com.equipxpress.domain.repositories.UserRoleRepository


class GetAllUserRolesUseCase(private val userRoleRepository: UserRoleRepository) {
    suspend operator fun invoke(): Result<List<UserRole>> {
        return try {
            val userRoles = userRoleRepository.getAll()
            Result.success(userRoles)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
