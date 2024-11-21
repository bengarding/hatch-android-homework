package co.hatch.model

import co.hatch.deviceClientLib.model.Device
import java.util.Date

class DeviceUiModel(
    val id: String,
    val name: String,
    val rssi: Int,
    val connected: Boolean,
    val elapsedSecsConnected: Long,
    val latestConnectedTime: Date?
) {
    companion object {
        fun createFromDevice(device: Device): DeviceUiModel {
            with(device) {
                return DeviceUiModel(id, name, rssi, connected, elapsedSecsConnected, latestConnectedTime)
            }
        }
    }
}
