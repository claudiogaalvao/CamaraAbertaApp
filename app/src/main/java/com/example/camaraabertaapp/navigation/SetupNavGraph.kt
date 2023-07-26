package com.example.camaraabertaapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.camaraabertaapp.presentation.Screen
import com.example.camaraabertaapp.presentation.SplashScreen
import com.example.camaraabertaapp.presentation.events.EventsScreen
import com.example.camaraabertaapp.presentation.preposition_themes.PrepositionThemesScreen
import com.example.camaraabertaapp.presentation.preposition_themes.PrepositionThemesViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    val viewModel = hiltViewModel<PrepositionThemesViewModel>()
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(
            route = Screen.SplashScreen.route
        ) {
            SplashScreen { destination ->
                navController.apply {
                    popBackStack()
                    navigate(destination)
                }
            }
        }
        composable(
            route = Screen.PrepositionThemesScreen.route
        ) {
            PrepositionThemesScreen(
                state = viewModel.state,
                onSelectTheme = viewModel::toggleSelection,
                onProceed = {
                    viewModel.saveOnboardFinished()
                    navController.apply {
                        popBackStack()
                        navigate(Screen.EventsScreen.route)
                    }
                }
            )
        }
        composable(
            route = Screen.EventsScreen.route
        ) {
            EventsScreen()
        }
    }
}