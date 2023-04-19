package com.ifsc.julio.javatcc.dto;

import lombok.*;
import java.util.HashMap;
import java.util.List;
import static com.ifsc.julio.javatcc.util.Const.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class DeviceTelemetryDTO {
    private List<TelemetryValueDTO> temperature;
    private List<TelemetryValueDTO> humidity;

    public HashMap<String, List<TelemetryValueDTO>> getDevices() {
        HashMap<String, List<TelemetryValueDTO>> devices = new HashMap<>();
        devices.put(TEMPERATURE, temperature);
        devices.put(HUMIDITY, humidity);
        return devices;
    }
}
