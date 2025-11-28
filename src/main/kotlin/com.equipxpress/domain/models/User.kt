package com.equipxpress.domain.models

data class User(
    val id: Int? = null,
    val nombre: String,
    val correo: String,
    val password: String,
    val rol: String
)
