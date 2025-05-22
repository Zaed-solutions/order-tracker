package com.zaed.ordertracker.app.navigation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.zaed.ordertracker.ui.flights.FlightsScreen
import com.zaed.ordertracker.ui.home.FlightDetailsScreen
import com.zaed.ordertracker.ui.login.LoginScreen

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val context = LocalContext.current
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Route.FlightsRoute,
        enterTransition = {
            fadeIn(
                animationSpec =
                    tween(
                        500,
                        easing = LinearEasing,
                    ),
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec =
                    tween(
                        500,
                        easing = LinearEasing,
                    ),
            )
        },
    ) {
        composable<Route.LoginRoute> {
            LoginScreen(
                onNavigateToHome = {
                    navController.navigate(Route.FlightsRoute) {
                        popUpTo(Route.LoginRoute) {
                            inclusive = true
                        }
                    }
                },
            )
        }
        composable<Route.FlightsRoute> {
            FlightsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToFlightDetails = {
                    navController.navigate(Route.FlightDetailsRoute(it))
                },
            )
        }
        composable<Route.FlightDetailsRoute> { navBackStackEntry ->
            val flightId = navBackStackEntry.toRoute<Route.FlightDetailsRoute>().flightId
            FlightDetailsScreen(
                flightId = flightId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
