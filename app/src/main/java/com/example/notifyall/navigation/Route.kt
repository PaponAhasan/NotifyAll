package com.example.notifyall.navigation

import androidx.compose.runtime.Composable

sealed class Route(val route: String) {

    //Nav
    object AuthNav : Route("auth_nav")
    object HomeNav : Route("main_nav")

    //Auth Nav
    object SplashScreen : Route("splash_screen")
    object WelcomeScreen : Route("welcome_screen")
    object LoginScreen : Route("login_screen")
    object RegisterScreen : Route("register_screen")

    //Main Nav
    object HomeScreen : Route("main_screen")

    // return with resulting route
    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}