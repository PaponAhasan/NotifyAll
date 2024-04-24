package com.example.notifyall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.notifyall.navigation.Navigation
import com.example.notifyall.ui.theme.NotifyAllTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotifyAllTheme {
                val systemUiController = rememberSystemUiController()
                val isDarkTheme = isSystemInDarkTheme() // Check for system dark theme

                val statusBarColor = if (isDarkTheme) Color.Black else Color.White

                // Set the background color for your content (optional)
                val contentColor = MaterialTheme.colorScheme.background

                DisposableEffect(systemUiController, isDarkTheme) {
                    systemUiController.setStatusBarColor(
                        color = statusBarColor,
                        darkIcons = !isDarkTheme
                    )
                    onDispose { } // This block will be executed when the activity is destroyed
                }

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = contentColor
                ) {
                    Navigation()
                }
            }
        }
    }
}