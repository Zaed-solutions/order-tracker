package com.zaed.ordertracker.app.data.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class User(
    val id: String = "",
    val username: String = "",
    val password: String = "",
    val createdAt: Instant = Clock.System.now(),
)
