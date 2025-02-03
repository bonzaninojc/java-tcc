package com.ifsc.julio.javatcc.dto;

import lombok.*;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class GraphicValueDTO {
    private String date;
    private Double value;
    private String key;
    private UUID stationId;
}
