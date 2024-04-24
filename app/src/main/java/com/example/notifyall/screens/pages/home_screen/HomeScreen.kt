package com.example.notifyall.screens.pages.home_screen

import android.widget.Button
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notifyall.navigation.NavRoute
import com.example.notifyall.screens.viemodels.UserViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    userViewModel: UserViewModel
) {
    val isDarkTheme = isSystemInDarkTheme()
    val showDialog = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.End
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(.2f)
                    .heightIn(min = 48.dp),
                contentPadding = PaddingValues(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isDarkTheme) Color.White else Color.Black,
                    contentColor = if (isDarkTheme) Color.Black else Color.White,
                ),
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    userViewModel.logout()
                    showDialog.value = true
                },
            ) {
                Text(text = "Logout", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }

        Text(text = "Main Screen")
    }

    // Show dialog if showDialog is true
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog
                showDialog.value = false
            },
            title = { Text("Logout Successful") },
            confirmButton = {
                Button(
                    onClick = {
                        // Handle confirmation action if needed
                        showDialog.value = false
                        navController.navigate(NavRoute.AuthNav.route) {
                            popUpTo(NavRoute.HomeNav.route) {
                                inclusive = true
                            }
                        }
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}