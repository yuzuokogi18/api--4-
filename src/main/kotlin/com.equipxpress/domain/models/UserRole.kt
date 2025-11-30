package com.equipxpress.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class UserRole(
    val id: Int? = null,
    val userId: Int,
    val roleId: Int
)