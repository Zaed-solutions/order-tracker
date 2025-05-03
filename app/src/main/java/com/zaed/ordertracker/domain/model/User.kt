package com.zaed.ordertracker.domain.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.util.Date

data class User(
    val id: String = "",
    val username: String = "",
    val password: String = "",
    val createdAt: Date = Date(),
)
