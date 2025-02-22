package com.ifsc.julio.javatcc.dto.thingsboard;

import com.ifsc.julio.javatcc.dto.TelemetryValueDTO;
import lombok.*;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ThingsboardValuesDTO {
    private List<TelemetryValueDTO> temperature;
    private List<TelemetryValueDTO> humidity;
}
