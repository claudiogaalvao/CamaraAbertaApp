package com.example.camaraabertaapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.camaraabertaapp.presentation.preposition_themes.PrepositionThemesScreen
import com.example.camaraabertaapp.presentation.preposition_themes.PrepositionThemesState
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
                    val viewModel = hiltViewModel<PrepositionThemesViewModel>()
                    PrepositionThemesScreen(
                        state = viewModel.state
                    ) { idTheme ->
                        viewModel.toggleSelection(idTheme)
                    }
//                    val navController = rememberNavController()
//                    NavHost(
//                        navController = navController,
//                        startDestination = Screen.PrepositionThemesScreen.route
//                    ) {
//                        composable(
//                            route = Screen.PrepositionThemesScreen.route
//                        ) {
//                            PrepositionThemesScreen(navController = navController)
//                        }
//                    }
                }
            }
        }
    }
}