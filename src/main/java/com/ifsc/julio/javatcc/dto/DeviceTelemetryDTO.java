package com.ifsc.julio.javatcc.dto;

import lombok.*;

import java.util.HashMap;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class DeviceTelemetryDTO {
    private List<TelemetryValueDTO> temperature;
    private List<TelemetryValueDTO> wind;

    public HashMap<String, List<TelemetryValueDTO>> getDevices() {
        HashMap<String, List<TelemetryValueDTO>> devices = new HashMap<>();
        devices.put("temperature", temperature);
        devices.put("wind", wind);
        return devices;
    }
}
