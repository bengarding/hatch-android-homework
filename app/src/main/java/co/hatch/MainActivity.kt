package co.hatch

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import co.hatch.navigation.NAV_ROUTE
import co.hatch.navigation.navigationGraph
import co.hatch.ui.theme.LocalActivity
import co.hatch.ui.theme.LocalNavController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            CompositionLocalProvider(
                LocalNavController provides navController,
                LocalActivity provides this@MainActivity
            ) {
                NavHost(
                    navController = navController,
                    startDestination = NAV_ROUTE,
                    modifier = Modifier.fillMaxSize()
                ) {
                    navigationGraph()
                }
            }
        }
    }
}
