package co.hatch.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import co.hatch.ui.theme.LocalActivity
import co.hatch.viewmodel.DevicesViewModel

@Composable
fun DetailsScreen(
    deviceId: String?,
    viewModel: DevicesViewModel = viewModel(LocalActivity.current)
) {

}