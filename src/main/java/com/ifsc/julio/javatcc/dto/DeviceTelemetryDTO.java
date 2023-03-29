package com.ifsc.julio.javatcc.dto;

import lombok.*;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class DeviceTelemetryDTO {
    private List<TelemetryValueDTO> temperature;
}
