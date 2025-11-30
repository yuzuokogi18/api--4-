package com.equipxpress.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val id: Int? = null,
    val nombre: String,
    val descripcion: String?,
    val imagen: String?,
    val categoriaId: Int?,
    val estado: String,
    val stock: Int,
    val createdAt: String? = null,
    val updatedAt: String? = null
)