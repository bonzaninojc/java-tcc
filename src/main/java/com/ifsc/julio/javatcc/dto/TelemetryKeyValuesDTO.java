package com.ifsc.julio.javatcc.dto;

import lombok.*;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class TelemetryKeyValuesDTO {
    private String key;
    private List<TelemetryValueDTO> values;
}
