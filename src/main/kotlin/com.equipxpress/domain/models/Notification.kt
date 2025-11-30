package com.equipxpress.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Notification(
    val id: Int? = null,
    val userId: Int,
    val requestId: Int?,
    val mensaje: String,
    val leida: Boolean = false,
    val createdAt: String? = null
)