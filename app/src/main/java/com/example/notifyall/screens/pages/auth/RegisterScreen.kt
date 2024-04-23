package com.example.notifyall.screens.pages.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.koijabencarowner.screens.healper.OutlinedPasswordField
import com.example.koijabencarowner.screens.healper.OutlinedTextField
import com.example.notifyall.R
import com.example.notifyall.navigation.Route
import com.example.notifyall.ui.theme.customTypography
import com.example.notifyall.ui.theme.gray40

@Composable
fun RegisterScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.dimen_16))
    ) {

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_128)))

        AutoResizedText(
            text = stringResource(id = R.string.register1),
            style = customTypography.titleMedium,
        ){}

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_8)))

        AutoResizedText(
            text = stringResource(id = R.string.register2),
            style = customTypography.labelSmall,
            color = gray40
        ){}

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_32)))

        OutlinedTextField(
            labelValue = stringResource(id = R.string.register3),
            painterResource = Icons.Filled.Email,
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_8)))

        OutlinedTextField(
            labelValue = stringResource(id = R.string.register5),
            painterResource = Icons.Filled.PersonAdd,
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_8)))

        OutlinedPasswordField(
            labelValue = stringResource(id = R.string.register4),
            painterResource =  Icons.Filled.Password,
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_8)))

        OutlinedPasswordField(
            labelValue = stringResource(id = R.string.register6),
            painterResource =  Icons.Filled.Password,
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_32)))

        ButtonComponentField(value = stringResource(id = R.string.register)){
            navController.popBackStack()
            navController.navigate(Route.LoginScreen.route)
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_16)))

        AutoResizedText(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.register7),
            style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.inter_regular))
            ),
        ){
            navController.popBackStack()
            navController.navigate(Route.LoginScreen.route)
        }
    }
}