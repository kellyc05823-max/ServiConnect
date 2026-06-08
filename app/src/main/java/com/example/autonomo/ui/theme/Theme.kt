package com.example.autonomo.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary            = Primary600,
    onPrimary          = White,
    primaryContainer   = Primary100,
    onPrimaryContainer = Primary900,
    secondary          = Accent500,
    onSecondary        = White,
    secondaryContainer = Accent100,
    onSecondaryContainer = Neutral900,
    background         = Neutral50,
    onBackground       = Neutral900,
    surface            = White,
    onSurface          = Neutral900,
    surfaceVariant     = Neutral100,
    onSurfaceVariant   = Neutral600,
    outline            = Neutral300,
    outlineVariant     = Neutral200,
    error              = Error500,
    onError            = White,
    errorContainer     = Error100,
    onErrorContainer   = Neutral900,
    inverseSurface     = Neutral800,
    inverseOnSurface   = Neutral50,
    inversePrimary     = Primary300,
)

private val DarkColorScheme = darkColorScheme(
    primary            = Primary300,
    onPrimary          = Primary900,
    primaryContainer   = Primary700,
    onPrimaryContainer = Primary100,
    secondary          = Accent400,
    onSecondary        = Neutral900,
    secondaryContainer = Accent500,
    onSecondaryContainer = White,
    background         = Primary900,
    onBackground       = Neutral100,
    surface            = Neutral800,
    onSurface          = Neutral100,
    surfaceVariant     = Neutral700,
    onSurfaceVariant   = Neutral400,
    outline            = Neutral600,
    outlineVariant     = Neutral700,
    error              = Error500,
    onError            = White,
    errorContainer     = Error100,
    onErrorContainer   = Error500,
    inverseSurface     = Neutral100,
    inverseOnSurface   = Neutral900,
    inversePrimary     = Primary600,
)

@Composable
fun ServiConnectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = AppTypography,
        content     = content
    )
}