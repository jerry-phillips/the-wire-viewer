package com.sample.wireviewer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


private val DarkColorPalette = darkColorScheme(
    primary = PrimaryDark,
    surfaceVariant = SecondaryLight,
    secondary = AccentLight,
    background = PrimaryDark,
    surface = AccentLight,
)

private val LightColorPalette = lightColorScheme(
    primary = PrimaryLight,
    surfaceVariant = SecondaryLight,
    secondary = SecondaryLight,
    background = Color.White,
    surface = Color.White,
)

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)

@Composable
fun TheWireTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}