package edu.kaist.cs.teamfinder.navigation

sealed class NavRoutes(val route: String) {
    object Home : NavRoutes("home")
    object Feed : NavRoutes("feed")
    object Add : NavRoutes("add")
    object Chat : NavRoutes("chat")
    object Save : NavRoutes("save")
}