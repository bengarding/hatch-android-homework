package co.hatch.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import co.hatch.ui.DetailsScreen
import co.hatch.ui.DevicesScreen
import co.hatch.ui.theme.LocalNavController

const val NAV_ROUTE = "navRoute"
const val DEVICE_ID = "deviceId"
const val DEVICE_NAME = "deviceName"

enum class NavigationRoute {
    DEVICES_LIST, DETAILS
}

fun NavGraphBuilder.navigationGraph() {
    navigation(
        startDestination = NavigationRoute.DEVICES_LIST.name,
        route = NAV_ROUTE
    ) {
        composable(NavigationRoute.DEVICES_LIST.name) {
            DevicesScreen()
        }
        composable(
            route = "${NavigationRoute.DETAILS.name}/{$DEVICE_ID}",
            arguments = listOf(navArgument(DEVICE_ID) { type = NavType.StringType })
        ) { backStackEntry ->
            val navController = LocalNavController.current
            val deviceId = backStackEntry.arguments?.getString(DEVICE_ID)
            DetailsScreen(
                deviceId = deviceId,
                onBackWithName = { name ->
                    navController.previousBackStackEntry?.savedStateHandle?.set(DEVICE_NAME, name)
                    navController.popBackStack()
                }
            )
        }
    }
}