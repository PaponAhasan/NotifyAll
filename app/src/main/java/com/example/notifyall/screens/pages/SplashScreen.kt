package com.example.koijabencarowner.screens.pages

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notifyall.R
import com.example.notifyall.navigation.NavRoute
import com.example.notifyall.screens.viemodels.UserViewModel
import com.example.notifyall.ui.theme.Sky40
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    userViewModel: UserViewModel
) {
    val scale = remember {
        Animatable(0f)
    }
    val alpha = remember {
        Animatable(0f)
    }
    val isUserLoggedIn = userViewModel.navigateToLogin.value

    LaunchedEffect(key1 = true) {
        //Logo Animation
        scale.animateTo(
            targetValue = 1.0f,
            animationSpec = tween(
                durationMillis = 1500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        //Text Animation
        alpha.animateTo(
            targetValue = 1.0f,
            animationSpec = tween(2500)
        )
        delay(2500L)
        navController.popBackStack()

        // Check if the user is logged in
        if (isUserLoggedIn == true) {
            navController.navigate(NavRoute.HomeNav.route)
        } else {
            // Navigate to the login screen if the user is not logged in
            navController.navigate(NavRoute.AuthNav.route)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        //.background(if (isSystemInDarkTheme()) Color.Black else Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val annotatedText = buildAnnotatedString {
            val textColor = if (isSystemInDarkTheme()) Color.White else Color.Black
            withStyle(style = SpanStyle(color = textColor)) {
                append("Let's") // "Let's" will be blue
            }
            append(" ")
            withStyle(style = SpanStyle(color = Sky40)) {
                append("go") // "go" will be red
            }
        }

        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "Logo",
            modifier = Modifier.scale(scale.value)
        )

        Spacer(modifier = Modifier.height(20.dp))

        BasicText(
            text = annotatedText,
            modifier = Modifier.alpha(alpha.value),
            style = TextStyle(fontSize = 32.sp)
        )
    }
}
