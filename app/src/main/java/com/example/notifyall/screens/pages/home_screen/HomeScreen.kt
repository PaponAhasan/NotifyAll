package com.example.notifyall.screens.pages.home_screen

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationAdd
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.koijabencarowner.screens.healper.AutoResizedText
import com.example.koijabencarowner.screens.healper.ButtonComponentField
import com.example.koijabencarowner.screens.healper.OutlinedTextField
import com.example.koijabencarowner.screens.healper.ProgressLoader
import com.example.notifyall.R
import com.example.notifyall.fcm.NotificationBody
import com.example.notifyall.navigation.NavRoute
import com.example.notifyall.screens.viemodels.NotificationViewModel
import com.example.notifyall.screens.viemodels.UserViewModel
import com.example.notifyall.ui.theme.customTypography
import com.example.notifyall.ui.theme.gray40
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    notificationViewModel: NotificationViewModel
) {

    var notification by rememberSaveable {
        mutableStateOf("")
    }
    val isDarkTheme = isSystemInDarkTheme()
    val showDialog = remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    val state = userViewModel.userTypeState.collectAsState(initial = null)
    val notificationState = notificationViewModel.notificationState.collectAsState(initial = null)

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
                    showDialog.value = true
                },
            ) {
                Text(text = "Logout", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }

            if (userViewModel.userTypeValue.value == true) {
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_64)))

                AutoResizedText(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Admin Screen",
                    style = customTypography.titleMedium,
                    color = gray40
                ) {}

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_8)))

                OutlinedTextField(
                    labelValue = stringResource(id = R.string.notification1),
                    painterResource = Icons.Filled.NotificationAdd,
                    onValueChange = { value ->
                        notification = value
                        notificationViewModel.let {
                            it.notificationFieldState.value = it.notificationFieldState.value.copy(
                                notification = value
                            )
                        }
                    },
                    errorStatus = notificationViewModel.notificationFieldState.value.notificationError
                )

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_32)))

                ButtonComponentField(value = stringResource(id = R.string.notification2)) {
                    if (!notificationViewModel.validateInputField().first) {
                        Toast.makeText(
                            context,
                            notificationViewModel.validateInputField().second, Toast.LENGTH_LONG
                        ).show()
                        return@ButtonComponentField
                    }
                    scope.launch {
                        notificationViewModel.sendNotification(
                            NotificationBody(
                                title = "Notification",
                                message = notification
                            )
                        )
                    }
                }
                // Admin User
                LaunchedEffect(key1 = notificationViewModel) {
                    notificationViewModel.unsubscribeToTopic()
                }
            } else {
                // Normal User
                NormalUserView()
                LaunchedEffect(key1 = notificationViewModel) {
                    notificationViewModel.subscribeToTopic()
                }
            }
        }
    }

    // Show dialog if showDialog is true
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog
                showDialog.value = false
            },
            title = { Text("Are you sure?") },
            confirmButton = {
                Button(
                    onClick = {
                        // Handle confirmation action if needed
                        showDialog.value = false
                        userViewModel.logout()
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

    LaunchedEffect(Unit) {
        scope.launch {
            userViewModel.getCurrentUserType()
        }
    }

    LaunchedEffect(key1 = state.value?.isSuccess) {
        scope.launch {
            if (state.value?.isSuccess?.isNotEmpty() == true) {
                //val success = state.value?.isSuccess
                //Toast.makeText(context, "$success", Toast.LENGTH_LONG).show()
            }
        }
    }

    LaunchedEffect(key1 = notificationState.value?.isSuccess) {
        scope.launch {
            if (notificationState.value?.isSuccess?.isNotEmpty() == true) {
                val success = state.value?.isSuccess
                Toast.makeText(context, "$success", Toast.LENGTH_LONG).show()
            }
        }
    }

    LaunchedEffect(key1 = state.value?.isError) {
        scope.launch {
            if (state.value?.isError?.isNotBlank() == true) {
                val error = state.value?.isError
                Toast.makeText(context, "$error", Toast.LENGTH_LONG).show()
            }
        }
    }

    LaunchedEffect(key1 = notificationState.value?.isError) {
        scope.launch {
            if (notificationState.value?.isError?.isNotBlank() == true) {
                val error = notificationState.value?.isError
                Toast.makeText(context, "$error message", Toast.LENGTH_LONG).show()
            }
        }
    }

    if (notificationState.value?.isLoading == true) ProgressLoader()

    if (state.value?.isLoading == true) ProgressLoader()
}