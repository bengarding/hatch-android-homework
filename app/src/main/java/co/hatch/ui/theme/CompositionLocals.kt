package co.hatch.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController

val LocalNavController = staticCompositionLocalOf<NavController> {
    error("No NavController found. Be sure you have properly set the CompositionLocalProvider")
}