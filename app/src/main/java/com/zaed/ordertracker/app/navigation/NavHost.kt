package com.zaed.ordertracker.app.navigation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zaed.ordertracker.ui.login.LoginScreen

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Route.LoginRoute,
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
                onNavigateToHome= {
//                    TODO("Navigate to home")
                }
            )
        }
    }
}
