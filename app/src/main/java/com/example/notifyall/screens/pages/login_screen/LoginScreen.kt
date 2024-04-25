package com.example.notifyall.screens.pages.login_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.koijabencarowner.screens.healper.AutoResizedText
import com.example.koijabencarowner.screens.healper.ButtonComponentField
import com.example.koijabencarowner.screens.healper.OutlinedEmailField
import com.example.koijabencarowner.screens.healper.OutlinedPasswordField
import com.example.koijabencarowner.screens.healper.ProgressLoader
import com.example.notifyall.R
import com.example.notifyall.navigation.NavRoute
import com.example.notifyall.screens.viemodels.LoginViewModel
import com.example.notifyall.ui.theme.customTypography
import com.example.notifyall.ui.theme.gray40
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel
) {

    var email by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val state = loginViewModel.logInState.collectAsState(initial = null)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.dimen_16))
    ) {

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_128)))

        AutoResizedText(
            text = stringResource(id = R.string.login1),
            style = customTypography.titleMedium,
        ) {}

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_8)))

        AutoResizedText(
            text = stringResource(id = R.string.login2),
            style = customTypography.labelSmall,
            color = gray40
        ) {}

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_32)))

        OutlinedEmailField(
            labelValue = stringResource(id = R.string.login3),
            painterResource = Icons.Filled.Email,
            onValueChange = { value ->
                email = value
                loginViewModel.let {
                    it.logInFieldState.value = it.logInFieldState.value.copy(
                        email = value
                    )
                }
            },
            errorStatus = loginViewModel.logInFieldState.value.emailError
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_8)))

        OutlinedPasswordField(
            labelValue = stringResource(id = R.string.login4),
            painterResource = Icons.Filled.Password,
            onValueChange = { value ->
                password = value
                loginViewModel.let {
                    it.logInFieldState.value = it.logInFieldState.value.copy(
                        password = value
                    )
                }
            },
            errorStatus = loginViewModel.logInFieldState.value.passwordError
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_32)))

        ButtonComponentField(value = stringResource(id = R.string.login)) {
            if (!loginViewModel.validateInputField().first) {
                Toast.makeText(
                    context,
                    loginViewModel.validateInputField().second, Toast.LENGTH_LONG
                ).show()
                return@ButtonComponentField
            }
            scope.launch {
                loginViewModel.login(email, password)
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_16)))

        AutoResizedText(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.login5),
            style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.inter_regular))
            ),
        ) {
            navController.popBackStack()
            navController.navigate(NavRoute.RegisterScreen.route)
        }

        LaunchedEffect(key1 = state.value?.isSuccess) {
            scope.launch {
                if (state.value?.isSuccess?.isNotEmpty() == true) {
                    val success = state.value?.isSuccess
                    navController.navigate(NavRoute.HomeNav.route) {
                        popUpTo(NavRoute.AuthNav.route) {
                            inclusive = true
                        }
                    }
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

        if (state.value?.isLoading == true) ProgressLoader()
    }
}