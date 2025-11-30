package com.equipxpress.application.usecases.userroles

import com.equipxpress.domain.models.UserRole
import com.equipxpress.domain.repositories.UserRoleRepository

class GetUserRolesByRoleIdUseCase(private val userRoleRepository: UserRoleRepository) {
    suspend operator fun invoke(roleId: Int): Result<List<UserRole>> {
        return try {
            val userRoles = userRoleRepository.getByRoleId(roleId)
            Result.success(userRoles)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}