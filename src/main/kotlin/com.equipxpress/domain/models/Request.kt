package com.equipxpress.domain.models

data class Request(
    val id: Int? = null,
    val userId: Int,
    val itemId: Int,
    val estado: String
)
