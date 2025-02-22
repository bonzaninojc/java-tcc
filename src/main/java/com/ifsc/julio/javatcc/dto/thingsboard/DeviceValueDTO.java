package com.ifsc.julio.javatcc.dto.thingsboard;

import lombok.*;
import java.util.UUID;
import static java.util.Objects.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class DeviceValueDTO {
    private Long ts;
    private Double value;
    private String stationId;

    public UUID getStationUUID() {
        return nonNull(stationId) ? UUID.fromString(stationId) : null;
    }
}
