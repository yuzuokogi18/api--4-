package com.equipxpress.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Request(
    val id: Int? = null,
    val userId: Int,
    val itemId: Int,
    val estado: String,
    val fechaSolicitud: String? = null,
    val fechaRespuesta: String? = null
)