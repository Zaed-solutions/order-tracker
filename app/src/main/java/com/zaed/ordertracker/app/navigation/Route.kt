package com.zaed.ordertracker.app.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object FlightsRoute : Route

    @Serializable
    data object LoginRoute : Route

    @Serializable
    data class FlightDetailsRoute(
        val flightId: String,
    ) : Route

    @Serializable
    data class MasterPackageDetailsRoute(
        val masterPackageId: String,
    ) : Route

    @Serializable
    data class MasterPackageGroupDetailsRoute(
        val masterPackageGroupId: String,
    ) : Route

    @Serializable
    data object HomeRoute : Route
}
