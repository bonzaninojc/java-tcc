package com.ifsc.julio.javatcc.dto;

import lombok.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceTelemetryHourDTO {
    private Double average;
    private Date hour;
    private String key;
}

