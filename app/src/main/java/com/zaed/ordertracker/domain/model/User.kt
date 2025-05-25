package com.zaed.ordertracker.domain.model

import java.util.Date

data class User(
    val id: String = "",
    val username: String = "",
    val password: String = "",
    val admin: Boolean = false,
    val createdAt: Date = Date(),
)
