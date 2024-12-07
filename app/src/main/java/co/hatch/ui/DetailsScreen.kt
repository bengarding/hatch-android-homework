package co.hatch.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.hatch.model.DeviceUiModel
import co.hatch.ui.theme.LocalActivity
import co.hatch.viewmodel.DevicesViewModel

@Composable
fun DetailsScreen(
    deviceId: String?,
    onBackWithName: (String?) -> Unit,
    viewModel: DevicesViewModel = viewModel(LocalActivity.current)
) {
    val device = viewModel.getDeviceFromId(deviceId)

    BackHandler {
        onBackWithName(device?.name)
    }

    if (device == null) {
        NoDeviceFound()
    } else {
        DeviceDetails(
            device = device,
            connectToDevice = { viewModel.connectToDevice(device) },
            disconnectFromDevice = { viewModel.disconnectFromDevice(device) },
            onSaveClicked = { name -> viewModel.updateName(device, name) }
        )
    }
}

@Composable
private fun NoDeviceFound() {
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
            text = "Error retrieving device",
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
private fun DeviceDetails(
    device: DeviceUiModel,
    connectToDevice: () -> Unit,
    disconnectFromDevice: () -> Unit,
    onSaveClicked: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(all = 12.dp)
    ) {
        DeviceItemDetail(
            label = "Name",
            value = device.name
        )
        DeviceItemDetail(
            label = "RSSI",
            value = device.rssi.toString()
        )
        DeviceItemDetail(
            label = "ID",
            value = device.id
        )
        DeviceItemDetail(
            label = "Connected",
            value = device.connected.toString()
        )
        DeviceItemDetail(
            label = "Last connected time",
            value = device.latestConnectedTime.toString()
        )
        DeviceItemDetail(
            label = "Elapsed seconds connected",
            value = device.elapsedSecsConnected.toString()
        )

        ConnectionButtons(
            connectToDevice = connectToDevice,
            disconnectFromDevice = disconnectFromDevice
        )

        EditName(
            onSaveClicked = onSaveClicked
        )
    }
}

@Composable
private fun DeviceItemDetail(label: String, value: String) {
    Row {
        Text(
            text = "$label:",
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            modifier = Modifier.padding(end = 4.dp)
        )
        Text(
            text = value,
            fontSize = 16.sp
        )
    }
}

@Composable
private fun ConnectionButtons(
    connectToDevice: () -> Unit,
    disconnectFromDevice: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(vertical = 20.dp)
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = connectToDevice
        ) {
            Text("Connect to device")
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = disconnectFromDevice
        ) {
            Text("Disconnect from device")
        }
    }
}

@Composable
private fun EditName(
    onSaveClicked: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        var newName by remember { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current

        TextField(
            value = newName,
            onValueChange = { newName = it },
            label = {
                Text("Enter new name")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                keyboardController?.hide()
                onSaveClicked(newName)
            }
        ) {
            Text("Save")
        }
    }
}