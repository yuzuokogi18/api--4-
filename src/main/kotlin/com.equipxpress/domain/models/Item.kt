package com.equipxpress.domain.models

data class Item(
    val id: Int? = null,
    val nombre: String,
    val descripcion: String?,
    val imagen: String?,
    val categoriaId: Int?,
    val estado: String,
    val stock: Int
)
