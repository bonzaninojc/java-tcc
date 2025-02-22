package com.ifsc.julio.javatcc.dto.thingsboard;

import lombok.*;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ThingsboardValuesDTO {
    private List<DeviceValueDTO> temperature;
    private List<DeviceValueDTO> humidity;
}
