package com.equipxpress.application.usecases.userroles

import com.equipxpress.domain.models.UserRole
import com.equipxpress.domain.repositories.UserRoleRepository


class GetUserRolesByUserIdUseCase(private val userRoleRepository: UserRoleRepository) {
    suspend operator fun invoke(userId: Int): Result<List<UserRole>> {
        return try {
            val userRoles = userRoleRepository.getByUserId(userId)
            Result.success(userRoles)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
