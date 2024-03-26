package com.claudiogalvaodev.camaraaberta

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.claudiogalvaodev.camaraaberta.ui.screens.eventDetails.EventDetailsScreen
import com.claudiogalvaodev.camaraaberta.ui.screens.events.EventsScreen
import com.claudiogalvaodev.camaraaberta.ui.navigation.Screen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.EventsScreen.route) {
                composable(Screen.EventsScreen.route) {
                    EventsScreen(
                        navigateToEventDetails = { eventId ->
                            navController.navigate(
                                Screen.EventDetailsScreen.withArgs(eventId.toString())
                            )
                        }
                    )
                }
                composable(
                    route = Screen.EventDetailsScreen.route + "/{eventId}",
                    arguments = listOf(
                        navArgument("eventId") {
                            type = NavType.IntType
                            defaultValue = 0
                            nullable = false
                        }
                    )
                ) { entry ->
                    EventDetailsScreen(entry.arguments?.getInt("eventId") ?: 0)
                }
            }
        }
    }
}