package br.com.fiap.emailapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,

    onPrimary = Color.White,
    onSecondary = Color.Gray.copy(0.3f)
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,

    onPrimary = FontBlack,
    onSecondary = FontGray

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun EmailAppTheme(
    darkTheme: Boolean,
    selectedFontSize: TextUnit,
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val typography = Typography(
        headlineLarge = TextStyle(
            fontSize = selectedFontSize * 1.5f
        ),
        titleSmall = TextStyle(
            fontSize = selectedFontSize * 1.15f
        ),
        bodyLarge = TextStyle(
            fontSize = selectedFontSize
        ),
        bodyMedium = TextStyle(
            fontSize = selectedFontSize * 0.9f
        ),
        bodySmall = TextStyle(
            fontSize = selectedFontSize * 0.8f
        )
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}