package com.zaed.ordertracker.domain.utils

import java.security.MessageDigest

fun hashWithMD5(input: String): String {
    val messageDigest = MessageDigest.getInstance("MD5")
    return messageDigest.digest(input.toByteArray()).joinToString("") { "%02x".format(it) }
}