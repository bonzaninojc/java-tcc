package com.ifsc.julio.javatcc.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class TelemetryValueDTO {
    private Long ts;
    private Double value;
}
