package com.ifsc.julio.javatcc.dto;

import lombok.*;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class TelemetryValueDTO {
    private Long ts;
    private Double value;
    private String stationId;

    public UUID getStationUUID() {
        return UUID.fromString(stationId);
    }
}
