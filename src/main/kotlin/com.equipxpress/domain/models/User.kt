package com.equipxpress.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int? = null,
    val nombre: String,
    val correo: String,
    val password: String,
    val rol: String,
    val createdAt: String? = null,
    val updatedAt: String? = null
)