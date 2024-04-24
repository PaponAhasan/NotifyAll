package com.example.notifyall.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.koijabencarowner.screens.pages.SplashScreen
import com.example.koijabencarowner.screens.pages.auth.LoginScreen
import com.example.koijabencarowner.screens.pages.auth.WelcomeScreen
import com.example.koijabencarowner.screens.pages.home.HomeScreen
import com.example.notifyall.screens.pages.register_screen.RegisterScreen
import com.example.notifyall.screens.viemodels.LoginViewModel
import com.example.notifyall.screens.viemodels.RegisterViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val time = 2500

    NavHost(
        navController = navController,
        startDestination = NavRoute.SplashScreen.route,
        //Animation added
        enterTransition = {
            fadeIn(animationSpec = tween(time)) + slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(time)
            )
        },
        exitTransition = {
            fadeOut(animationSpec = tween(time)) + slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Down,
                animationSpec = tween(time)
            )
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(time)) + slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Up,
                animationSpec = tween(time)
            )
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(time)) + slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(time)
            )
        },
    ) {
        /* ----------------- */
        composable(route = NavRoute.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        /* ----------------- */
        navigation(
            startDestination = NavRoute.WelcomeScreen.route,
            route = NavRoute.AuthNav.route
        ) {
            composable(route = NavRoute.WelcomeScreen.route) {
                WelcomeScreen(navController = navController)
            }
            composable(route = NavRoute.LoginScreen.route) {
                val viewModel = it.sharedViewModel<LoginViewModel>(navController = navController)
                LoginScreen(navController = navController, loginViewModel = viewModel)
            }
            composable(route = NavRoute.RegisterScreen.route) {
                val viewModel = it.sharedViewModel<RegisterViewModel>(navController = navController)
                RegisterScreen(navController = navController, registerViewModel = viewModel)
            }
        }
        /* ----------------- */
        navigation(
            startDestination = NavRoute.HomeScreen.route,
            route = NavRoute.HomeNav.route
        ) {
            composable(route = NavRoute.HomeScreen.route) {
                HomeScreen(navController = navController)
            }
        }

    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}