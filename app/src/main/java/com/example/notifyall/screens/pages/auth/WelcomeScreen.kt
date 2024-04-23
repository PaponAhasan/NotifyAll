package com.example.koijabencarowner.screens.pages.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.koijabencarowner.screens.healper.AutoResizedText
import com.example.koijabencarowner.screens.healper.ButtonComponentField
import com.example.koijabencarowner.screens.healper.OutlinedButtonComponentField
import com.example.notifyall.R
import com.example.notifyall.navigation.Route
import com.example.notifyall.ui.theme.customTypography
import com.example.notifyall.ui.theme.gray40

@Composable
fun WelcomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.dimen_16)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_16)))

        Image(
            painter = painterResource(id = R.drawable.img_welcome),
            contentDescription = "Welcome"
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_64)))

        AutoResizedText(
            text = stringResource(id = R.string.welcome1),
            style = customTypography.titleLarge,
        ){}

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_5)))

        AutoResizedText(
            text = stringResource(id = R.string.welcome2),
            style = customTypography.labelSmall,
            color = gray40
        ){}

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_32)))

        ButtonComponentField(value = stringResource(id = R.string.new_account)){
            navController.navigate(Route.RegisterScreen.route)
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_16)))

        OutlinedButtonComponentField(value = stringResource(id = R.string.login)){
            navController.navigate(Route.LoginScreen.route)
        }
    }
}
