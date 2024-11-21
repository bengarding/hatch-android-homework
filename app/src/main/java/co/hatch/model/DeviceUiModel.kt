package co.hatch.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import co.hatch.deviceClientLib.model.Device
import java.util.Date

class DeviceUiModel(
    id: String,
    name: String,
    rssi: Int,
    connected: Boolean,
    elapsedSecsConnected: Long,
    latestConnectedTime: Date?
) {
    companion object {
        fun createFromDevice(device: Device): DeviceUiModel {
            with(device) {
                return DeviceUiModel(id, name, rssi, connected, elapsedSecsConnected, latestConnectedTime)
            }
        }
    }

    var id by mutableStateOf(id)
    var name by mutableStateOf(name)
    var rssi by mutableIntStateOf(rssi)
    var connected by mutableStateOf(connected)
    var elapsedSecsConnected by mutableLongStateOf(elapsedSecsConnected)
    var latestConnectedTime by mutableStateOf(latestConnectedTime)

    fun updateFromDevice(device: Device) {
        id = device.id
        name = device.name
        rssi = device.rssi
        connected = device.connected
        elapsedSecsConnected = device.elapsedSecsConnected
        latestConnectedTime = device.latestConnectedTime
    }
}
