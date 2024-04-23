package com.example.koijabencarowner.screens.healper

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isUnspecified
import androidx.compose.ui.unit.sp
import com.example.notifyall.R

val componentShapes = Shapes(
    small = RoundedCornerShape(5.dp),
    medium = RoundedCornerShape(10.dp),
    large = RoundedCornerShape(0.dp),
)

@Composable
fun isSmallScreenHeight(): Boolean {
    val conf = LocalConfiguration.current
    return conf.screenHeightDp <= 786
}

@Composable
fun AutoResizedText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
    color: Color = style.color,
    onclick: () -> Unit
) {
    var resizedTextStyle by remember {
        mutableStateOf(style)
    }
    var shouldDraw by remember {
        mutableStateOf(false)
    }

    val defaultFontSize = MaterialTheme.typography.bodyMedium.fontSize

    Text(
        text = text,
        color = color,
        modifier = modifier.drawWithContent {
            if (shouldDraw) {
                drawContent()
            }
        }.clickable { onclick() },
        softWrap = false,
        style = resizedTextStyle,
        onTextLayout = { result ->
            if (result.didOverflowWidth) {
                if (style.fontSize.isUnspecified) {
                    resizedTextStyle = resizedTextStyle.copy(
                        fontSize = defaultFontSize
                    )
                }
                resizedTextStyle = resizedTextStyle.copy(
                    fontSize = resizedTextStyle.fontSize * 0.95
                )
            } else {
                shouldDraw = true
            }
        }
    )
}

@Composable
fun OutlinedTextField(
    modifier: Modifier = Modifier,
    labelValue: String? = null,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    maxLines: Int = Int.MAX_VALUE,
    painterResource: ImageVector? = null,
) {
    var textValue by remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .clip(componentShapes.small),
        value = textValue,
        label = { Text(text = labelValue ?: "") },
        onValueChange = { textValue = it },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        maxLines = maxLines,
        leadingIcon = {
            if (painterResource != null) {
                Icon(imageVector = painterResource, contentDescription = "")
            }
        }
    )
}

@Composable
fun OutlinedPasswordField(
    modifier: Modifier = Modifier,
    labelValue: String? = null,
    maxLines: Int = Int.MAX_VALUE,
    painterResource: ImageVector? = null,
) {
    var password by remember {
        mutableStateOf("")
    }

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .clip(componentShapes.small),
        value = password,
        label = { Text(text = labelValue ?: "") },
        onValueChange = { password = it },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        maxLines = maxLines,
        leadingIcon = {
            if (painterResource != null) {
                Icon(imageVector = painterResource, contentDescription = "")
            }
        },
        trailingIcon = {
            val image = if (passwordVisible) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }

            val description = if (passwordVisible)
                stringResource(id = R.string.password_hide)
            else stringResource(id = R.string.password_show)

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, contentDescription = description)
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
    )
}

@Composable
fun ButtonComponentField(
    modifier: Modifier = Modifier,
    value: String,
    onclick: () -> Unit
) {
    val isDarkTheme = isSystemInDarkTheme()

    Button(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isDarkTheme) Color.White else Color.Black,
            contentColor = if (isDarkTheme) Color.Black else Color.White,
        ),
        shape = RoundedCornerShape(8.dp),
        onClick = onclick,
    ) {
        Text(text = value, fontSize = 14.sp, fontWeight = Bold)
    }
}

@Composable
fun OutlinedButtonComponentField(
    modifier: Modifier = Modifier,
    value: String,
    onclick: () -> Unit
) {
    val isDarkTheme = isSystemInDarkTheme()

    OutlinedButton(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isDarkTheme) Color.Black else Color.White,
            contentColor = if (isDarkTheme) Color.White else Color.Black,
        ),
        shape = RoundedCornerShape(8.dp),
        onClick = onclick,
    ) {
        Text(text = value, fontSize = 14.sp, fontWeight = Bold)
    }
}

/*
01. Shimmer Loading Effect -> https://www.youtube.com/watch?v=NyO99OJPPec
02. Sharing Data Between Screens -> https://www.youtube.com/watch?v=h61Wqy3qcKg&t=406s
 */
