package co.hatch.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import co.hatch.ui.DetailsScreen
import co.hatch.ui.DevicesScreen

const val NAV_ROUTE = "navRoute"

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
            route = "${NavigationRoute.DETAILS.name}/{deviceId}",
            arguments = listOf(navArgument("deviceId") { type = NavType.StringType })
        ) { backStackEntry ->
            val deviceId = backStackEntry.arguments?.getString("deviceId")
            DetailsScreen(deviceId)
        }
    }
}