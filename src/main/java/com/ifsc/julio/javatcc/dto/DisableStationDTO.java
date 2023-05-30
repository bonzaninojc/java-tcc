package com.ifsc.julio.javatcc.dto;

import lombok.*;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class DisableStationDTO {
    private UUID stationId;
    private boolean disabled;
}
