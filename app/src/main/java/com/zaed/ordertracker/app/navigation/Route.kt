package com.zaed.ordertracker.app.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object FlightsRoute : Route

    @Serializable
    data object LoginRoute : Route
}
