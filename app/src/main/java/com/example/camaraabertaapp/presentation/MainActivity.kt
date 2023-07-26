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
import com.example.camaraabertaapp.navigation.SetupNavGraph
import com.example.camaraabertaapp.presentation.events.EventsScreen
import com.example.camaraabertaapp.presentation.preposition_themes.PrepositionThemesScreen
import com.example.camaraabertaapp.presentation.preposition_themes.PrepositionThemesViewModel
import com.example.camaraabertaapp.presentation.ui.theme.CamaraAbertaAppTheme
import dagger.hilt.android.AndroidEntryPoint

// TODO List
// Solve technical debts
// Refactor the project to implement best practices
// - Events
// - Interfaces
// - Use cases

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
                    SetupNavGraph(navController = navController)
                }
            }
        }
    }
}