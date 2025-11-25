package com.nafisa008.nafisa008recipe.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// 🎨 BRAND COLORS
private val SoftYellow = Color(0xFFFFFBE6)
private val PrimaryYellow = Color(0xFFFFCC40)
private val LightYellow = Color(0xFFFFEFA6)
private val DarkBrownText = Color(0xFF4E342E)
private val OutlineSoft = Color(0xFFC7A869)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryYellow,
    onPrimary = DarkBrownText,
    primaryContainer = LightYellow,
    onPrimaryContainer = DarkBrownText,

    secondary = LightYellow,
    onSecondary = DarkBrownText,

    background = SoftYellow,
    onBackground = DarkBrownText,

    surface = SoftYellow,
    onSurface = DarkBrownText,

    outline = OutlineSoft
)

// 🌙 You can customize dark theme later if you want
private val DarkColorScheme = darkColorScheme(
    primary = PrimaryYellow,
    onPrimary = Color.Black,
    background = Color(0xFF1B1B1B),
    onBackground = Color.White
)

@Composable
fun Nafisa008RecipeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // DISABLED to avoid Android recoloring your palette
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
