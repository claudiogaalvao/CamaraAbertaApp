package com.example.camaraabertaapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.camaraabertaapp.ui.screen.Screen
import com.example.camaraabertaapp.ui.screen.SplashScreen
import com.example.camaraabertaapp.ui.screen.events.EventsScreen
import com.example.camaraabertaapp.ui.screen.preposition_themes.PrepositionThemesScreen
import com.example.camaraabertaapp.ui.screen.preposition_themes.PrepositionThemesScreenEvent
import com.example.camaraabertaapp.ui.screen.preposition_themes.PrepositionThemesViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
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
            val viewModel = hiltViewModel<PrepositionThemesViewModel>()
            PrepositionThemesScreen(
                state = viewModel.state,
                prepositionThemesScreenEvent = PrepositionThemesScreenEvent(
                    onSelectTheme = viewModel::toggleSelection,
                    onProceed = {
                        viewModel.saveOnboardFinished()
                        navController.apply {
                            popBackStack()
                            navigate(Screen.EventsScreen.route)
                        }
                    }
                )
            )
        }
        composable(
            route = Screen.EventsScreen.route
        ) {
            EventsScreen()
        }
    }
}