package com.claudiogalvaodev.camaraaberta.ui.navigation

sealed class Screen(val route: String) {
    object EventsScreen : Screen("events_screen")
    object EventDetailsScreen : Screen("event_details_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}