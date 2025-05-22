package com.zaed.ordertracker.ui.util

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.char

fun LocalDateTime.formatDateTime(): String {
    val customFormat =
        LocalDateTime.Format {
            year()
            char('/')
            monthNumber()
            char('/')
            dayOfMonth()
            char(' ')
            hour()
            char(':')
            minute()
            char(' ')
            amPmMarker("am", "pm")
        }
    return this.format(customFormat)
}

fun LocalDateTime.formatTime(): String {
    val customFormat =
        LocalDateTime.Format {
            hour()
            char(':')
            minute()
            char(' ')
            amPmMarker("am", "pm")
        }
    return this.format(customFormat)
}

fun LocalDateTime.formatDate(): String {
    val customFormat =
        LocalDateTime.Format {
            year()
            char('/')
            monthNumber()
            char('/')
            dayOfMonth()
        }
    return this.format(customFormat)
}
