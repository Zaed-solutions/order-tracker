package com.zaed.ordertracker.domain.model

import com.zaed.ordertracker.R
import java.util.Date

data class User(
    val id: String = "",
    val username: String = "",
    val password: String = "",
    val admin: Boolean = false,
    val createdAt: Date = Date(),
){
    companion object{
        fun validateUsername(username: String): Pair<Boolean, Int> {
            return when {
                username.isEmpty() -> Pair(true, R.string.username_empty)
                username.length < 3 -> Pair(true, R.string.username_too_short)
                username.length > 20 -> Pair(true, R.string.username_too_long)
                else -> Pair(false, 0)
            }
        }
        fun validatePassword(password: String): Pair<Boolean, Int> {
            return when {
                password.isEmpty() -> Pair(true, R.string.password_empty)
                password.length < 6 -> Pair(true, R.string.password_too_short)
                password.length > 20 -> Pair(true, R.string.password_too_long)
                else -> Pair(false, 0)
            }
        }
    }
}
