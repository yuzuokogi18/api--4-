package com.equipxpress.domain.repositories

import com.equipxpress.domain.models.Role

interface RoleRepository {
    suspend fun getAll(): List<Role>
    suspend fun getById(id: Int): Role?
    suspend fun create(role: Role): Role
    suspend fun update(id: Int, role: Role): Role?  // ← Faltaba este método
    suspend fun delete(id: Int): Boolean
}