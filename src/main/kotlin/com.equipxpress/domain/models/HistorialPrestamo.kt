package com.equipxpress.domain.models

data class HistorialPrestamo(
    val id: Int? = null,
    val requestId: Int,
    val fechaEntrega: String? = null,
    val fechaDevolucion: String? = null,
    val estadoPrestamo: String,
    val observaciones: String? = null
)
