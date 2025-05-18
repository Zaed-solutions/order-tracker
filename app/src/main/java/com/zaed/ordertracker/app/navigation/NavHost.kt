package com.zaed.ordertracker.app.navigation

import android.util.Log
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
import com.zaed.ordertracker.ui.login.LoginScreen
import com.zaed.ordertracker.ui.settings.SettingsScreen

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Route.SettingsRoute,
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
            val context = LocalContext.current
            LoginScreen(
                onNavigateToHome= {
//                    TODO("Navigate to home")
                    Toast.makeText(context, "Navigate to home", Toast.LENGTH_SHORT).show()
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
