package com.claudiogalvaodev.camaraaberta.ui.navigation

sealed class Screens(val route: String) {
    object EventsScreen : Screens("events_screen")
    object EventDetailsScreen : Screens("event_details_screen")
    object PropositionDetailsScreen : Screens("proposition_details_screen")
    object PropositionsScreen: Screens("propositions_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}