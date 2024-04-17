package com.claudiogalvaodev.camaraaberta.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(
        route = Screens.EventsScreen.route,
        label = "Agenda",
        icon = Icons.Default.Home
    ),
    BottomNavItem(
        route = Screens.PropositionsScreen.route,
        label = "Propostas",
        icon = Icons.Default.Description
    )
)