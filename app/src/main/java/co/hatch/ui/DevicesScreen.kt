package co.hatch.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.hatch.model.DeviceUiModel
import co.hatch.navigation.NavigationRoute
import co.hatch.ui.theme.LocalActivity
import co.hatch.ui.theme.LocalNavController
import co.hatch.viewmodel.DevicesViewModel
import kotlinx.coroutines.delay

private const val UPDATE_TIME_MILLIS = 10000L

@Composable
fun DevicesScreen(
    viewModel: DevicesViewModel = viewModel(LocalActivity.current)
) {
    LaunchedEffect(Unit) {
        while (true) {
            delay(UPDATE_TIME_MILLIS)
            viewModel.updateDevices()
        }
    }

    Column {
        TableHeader()

        if (viewModel.isUpdating) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        } else {
            Spacer(modifier = Modifier.height(4.dp))
        }

        HorizontalDivider(modifier = Modifier.fillMaxWidth())

        val devices = viewModel.devicesFlow.collectAsState().value
        if (devices.isNotEmpty()) {
            DevicesList(devices)
        } else {
            NoDevicesFound()
        }
    }
}

@Composable
private fun TableHeader() {
    Row(
        modifier = Modifier.padding(start = 12.dp, top = 12.dp, end = 12.dp, bottom = 8.dp)
    ) {
        Text(
            text = "Device Name",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "RSSI",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.weight(2f)
        )
    }
}

@Composable
private fun DevicesList(
    devices: List<DeviceUiModel>
) {
    val navController = LocalNavController.current
    LazyColumn {
        items(devices) { device ->
            Row(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 4.dp)
                    .clickable { navController.navigate("${NavigationRoute.DETAILS.name}/${device.id}") }
            ) {
                Text(
                    text = device.name,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "${device.rssi}",
                    modifier = Modifier.weight(2f)
                )
            }
        }
    }
}

@Composable
private fun NoDevicesFound() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Icon(
            imageVector = Icons.Filled.Warning,
            contentDescription = null,
            Modifier.size(60.dp)
        )
        Text(
            text = "No devices found",
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
