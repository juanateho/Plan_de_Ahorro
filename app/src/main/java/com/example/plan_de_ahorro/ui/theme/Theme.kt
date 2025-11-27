package com.example.plan_de_ahorro.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Definimos un único esquema de colores oscuro y pastel
private val DarkPastelScheme = darkColorScheme(
    primary = PastelMint,        // Botones y acentos principales
    onPrimary = DeepBackground,  // Texto sobre botones primary
    primaryContainer = PastelMint.copy(alpha = 0.2f),
    onPrimaryContainer = PastelMint,
    
    secondary = PastelCoral,     // Acentos secundarios
    onSecondary = DeepBackground,
    secondaryContainer = PastelCoral.copy(alpha = 0.2f),
    onSecondaryContainer = PastelCoral,
    
    tertiary = PastelLilac,
    onTertiary = DeepBackground,
    
    background = DeepBackground, // Fondo general oscuro
    onBackground = TextPrimary,  // Texto principal
    
    surface = SurfaceCard,       // Tarjetas visiblemente diferentes
    onSurface = TextPrimary,     // Texto sobre tarjetas
    
    surfaceVariant = SurfaceVariant, // Elementos de entrada y variantes
    onSurfaceVariant = TextSecondary,
    
    error = PastelCoral,
    onError = DeepBackground
)

@Composable
fun Plan_de_AhorroTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Mantenemos el parámetro pero...
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    // ...Forzamos el esquema oscuro pastel siempre, o si prefieres respetar el sistema, usa la lógica condicional.
    // Dado que pediste explícitamente "interfaz con fondo oscuro", usaré el esquema oscuro por defecto
    // independientemente de la configuración del sistema para garantizar el look solicitado.
    
    val colorScheme = DarkPastelScheme 

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            // Iconos claros en la barra de estado porque el fondo es oscuro
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false 
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}