package com.equipxpress.domain.models
import kotlinx.serialization.Serializable

@Serializable  // ← AGREGADO
data class Role(
    val id: Int? = null,
    val nombre: String
)
