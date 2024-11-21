package co.hatch.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.hatch.deviceClientLib.connectivity.ConnectivityClient
import co.hatch.model.DeviceUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

class DevicesViewModel : ViewModel() {

    private val connectivityClient = ConnectivityClient.Factory.create()

    private val mutableDevicesFlow: MutableStateFlow<List<DeviceUiModel>> = MutableStateFlow(listOf())
    val devicesFlow: StateFlow<List<DeviceUiModel>> = mutableDevicesFlow

    var isUpdating by mutableStateOf(false)

    init {
        updateDevices()
    }

    fun updateDevices() {
        viewModelScope.launch(Dispatchers.IO) {
            isUpdating = true

            mutableDevicesFlow.value = connectivityClient.discoverDevices()
                // Creates Map<Int, Device>, which groups devices by the absolute value of RSSI. Using the absolute
                // value ensures that the sort order will be ascending (stronger signals have lower absolute values)
                .groupBy { it.rssi.absoluteValue }
                // Sort the map so that devices with the strongest signal (low RSSI abs) come first
                .toSortedMap()
                // For each group, sort devices by the value after the '#' in their name.
                .mapValues { it.value.sortedBy { device -> device.name.split('#').last().toIntOrNull() } }
                // Flatten into a single list of devices
                .flatMap { it.value }
                // Convert Devices into DeviceUiModels
                .map { DeviceUiModel.createFromDevice(it) }

            isUpdating = false
        }
    }

    fun getDeviceFromId(deviceId: String?): DeviceUiModel? = mutableDevicesFlow.value.find { it.id == deviceId }

    fun updateName(
        device: DeviceUiModel,
        name: String
    ) {
        if (device.connected) {
            viewModelScope.launch(Dispatchers.IO) {
                connectivityClient.updateDeviceName(device.id, name)
            }
        }
    }
}