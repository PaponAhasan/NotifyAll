package com.example.notifyall.navigation

sealed class NavRoute(val route: String) {

    //Nav
    object AuthNav : NavRoute("auth_nav")
    object HomeNav : NavRoute("main_nav")

    //Auth Nav
    object SplashScreen : NavRoute("splash_screen")
    object WelcomeScreen : NavRoute("welcome_screen")
    object LoginScreen : NavRoute("login_screen")
    object RegisterScreen : NavRoute("register_screen")

    //Main Nav
    object HomeScreen : NavRoute("main_screen")

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