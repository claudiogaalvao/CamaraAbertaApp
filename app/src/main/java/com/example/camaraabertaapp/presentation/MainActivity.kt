package com.example.camaraabertaapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.camaraabertaapp.presentation.events.EventsScreen
import com.example.camaraabertaapp.presentation.preposition_themes.PrepositionThemesScreen
import com.example.camaraabertaapp.presentation.preposition_themes.PrepositionThemesViewModel
import com.example.camaraabertaapp.presentation.ui.theme.CamaraAbertaAppTheme
import dagger.hilt.android.AndroidEntryPoint

// Verificar se já passou passou pela selecao de temas
// Ao clicar em pular, salva no banco que já passou pela selecao e vai para tela principal

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CamaraAbertaAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val viewModel = hiltViewModel<PrepositionThemesViewModel>()
                    val startDestination = if (viewModel.shouldInitFromOnboard) {
                        Screen.PrepositionThemesScreen.route
                    } else Screen.EventsScreen.route

                    NavHost(
                        navController = navController,
                        startDestination = startDestination
                    ) {
                        composable(
                            route = Screen.PrepositionThemesScreen.route
                        ) {
                            PrepositionThemesScreen(
                                state = viewModel.state,
                                onSelectTheme = viewModel::toggleSelection,
                                onProceed = {
                                    viewModel.saveOnboardFinished()
                                    navController.navigate(Screen.EventsScreen.route)
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
            }
        }
    }
}