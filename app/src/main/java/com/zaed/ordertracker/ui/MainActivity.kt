package com.zaed.ordertracker.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.zaed.ordertracker.app.navigation.NavigationHost
import com.zaed.ordertracker.ui.theme.ProjectTemplateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjectTemplateTheme {
                App()
            }
        }
    }

    @Composable
    private fun App() {
        val navController = rememberNavController()
        Scaffold(
            modifier =
                Modifier
                    .fillMaxSize()
                    .imePadding(),
        ) { paddingValues ->
            NavigationHost(
                modifier = Modifier.padding(paddingValues),
                navController = navController,
            )
        }
    }
}

//val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass
