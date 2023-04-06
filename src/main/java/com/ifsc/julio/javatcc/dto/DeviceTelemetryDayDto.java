package com.ifsc.julio.javatcc.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class DeviceTelemetryDayDto {
    private BigDecimal average;
    private Date day;
}
