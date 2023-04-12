package com.ifsc.julio.javatcc.dto;

import lombok.*;
import java.util.Date;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class DeviceTelemetryDayDTO {
    private Double average;
    private Date day;
    private String key;
}
