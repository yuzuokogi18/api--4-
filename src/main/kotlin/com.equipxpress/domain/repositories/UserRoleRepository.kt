package com.equipxpress.domain.repositories

import com.equipxpress.domain.models.UserRole

interface UserRoleRepository {
    suspend fun create(userRole: UserRole): UserRole
    suspend fun getAll(): List<UserRole>
    suspend fun getById(id: Int): UserRole?
    suspend fun getByUserId(userId: Int): List<UserRole>
    suspend fun getByRoleId(roleId: Int): List<UserRole>
    suspend fun delete(id: Int): Boolean
    suspend fun deleteByUserAndRole(userId: Int, roleId: Int): Boolean
}