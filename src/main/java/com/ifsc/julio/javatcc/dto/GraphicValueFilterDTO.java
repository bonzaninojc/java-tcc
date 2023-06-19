package com.ifsc.julio.javatcc.dto;

import lombok.*;
import java.util.Date;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class GraphicValueFilterDTO {
    private UUID stationId;
    private String key;
    private String average;
    private Date initDate;
    private Date finalDate;
}
