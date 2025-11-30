package com.equipxpress.application.usecases.userroles

import com.equipxpress.domain.models.UserRole
import com.equipxpress.domain.repositories.UserRoleRepository

class AssignRoleToUserUseCase(private val userRoleRepository: UserRoleRepository) {
    suspend operator fun invoke(userId: Int, roleId: Int): Result<UserRole> {
        return try {
            val userRole = UserRole(
                userId = userId,
                roleId = roleId
            )
            val created = userRoleRepository.create(userRole)
            Result.success(created)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}