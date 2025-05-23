package com.zaed.ordertracker.app.navigation

import android.widget.Toast
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
import com.zaed.ordertracker.ui.masterpackagedetails.MasterPackageDetailsScreen
import com.zaed.ordertracker.ui.masterpkggroupdetails.MasterPackageGroupDetailsScreen
import com.zaed.ordertracker.ui.settings.SettingsScreen

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
                },
                onNavigateToMasterPackageDetails = { masterPackageId ->
                    navController.navigate(Route.MasterPackageDetailsRoute(masterPackageId))
                },
                onNavigateToMasterPackageGroupDetails = { masterPackageGroupId ->
                    navController.navigate(Route.MasterPackageGroupDetailsRoute(masterPackageGroupId))
                }
            )
        }

        composable<Route.MasterPackageDetailsRoute> { navBackStackEntry ->
            val masterPackageId = navBackStackEntry.toRoute<Route.MasterPackageDetailsRoute>().masterPackageId
            MasterPackageDetailsScreen(
                masterPackageId = masterPackageId,
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }

        composable<Route.MasterPackageGroupDetailsRoute> { navBackStackEntry ->
            val masterPackageGroupId = navBackStackEntry.toRoute<Route.MasterPackageGroupDetailsRoute>().masterPackageGroupId
            MasterPackageGroupDetailsScreen(
                groupId = masterPackageGroupId,
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }
        composable<Route.SettingsRoute> {
            val context = LocalContext.current
            SettingsScreen(
                onNavigateBack = {
//                    TODO("Navigate back")
                    Toast.makeText(context, "Navigate Back", Toast.LENGTH_SHORT).show()
                },
            )
        }
    }
}
