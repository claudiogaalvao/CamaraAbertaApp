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
import com.claudiogalvaodev.camaraaberta.ui.screens.propositionDetails.PropositionDetailsScreen

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
                    EventDetailsScreen(
                        eventId = entry.arguments?.getInt("eventId") ?: 0,
                        navigateToPropositionDetails = { propositionId ->
                            Log.d("Claudio", "Navigate to proposition details: $propositionId")
                            navController.navigate(
                                Screen.PropositionDetailsScreen.withArgs(propositionId.toString())
                            )
                        }
                    )
                }
                composable(
                    route = Screen.PropositionDetailsScreen.route + "/{propositionId}",
                    arguments = listOf(
                        navArgument("propositionId") {
                            type = NavType.IntType
                            defaultValue = 0
                            nullable = false
                        }
                    )
                ) { entry ->
                    val propositionId = entry.arguments?.getInt("propositionId") ?: 0
                    Log.d("Claudio", "Proposition id from arguments: $propositionId")
                    PropositionDetailsScreen(propositionId = propositionId)
                }
            }
        }
    }
}