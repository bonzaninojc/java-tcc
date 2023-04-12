package com.ifsc.julio.javatcc.dto;

import lombok.*;
import java.util.HashMap;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class DeviceTelemetryDTO {
    private List<TelemetryValueDTO> temperature;

    public HashMap<String, List<TelemetryValueDTO>> getDevices() {
        HashMap<String, List<TelemetryValueDTO>> devices = new HashMap<>();
        devices.put("temperature", temperature);
        return devices;
    }
}
