package com.ifsc.julio.javatcc.dto;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class GraphicValueYearDTO {
    private String date;
    private Double value;
    private String key;
    private UUID stationId;

}
