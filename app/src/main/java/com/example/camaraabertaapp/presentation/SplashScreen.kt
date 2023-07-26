package com.example.camaraabertaapp.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.camaraabertaapp.presentation.preposition_themes.PrepositionThemesViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateForward: (destination: String) -> Unit
) {
    val viewModel = hiltViewModel<PrepositionThemesViewModel>()
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        )
    )

    val destination = if (viewModel.shouldInitFromOnboard) {
        Screen.PrepositionThemesScreen.route
    } else Screen.EventsScreen.route

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(4000)
        onNavigateForward(destination)
    }
    Splash(
        alpha = alphaAnim.value
    )
}

@Composable
private fun Splash(alpha: Float) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(120.dp)
                .alpha(alpha),
            imageVector = Icons.Default.Home,
            contentDescription = "Logo Home",
            tint = Color.White
        )
    }
}