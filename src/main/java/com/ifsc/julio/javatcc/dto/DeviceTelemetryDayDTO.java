package com.ifsc.julio.javatcc.dto;

import com.ifsc.julio.javatcc.enumeration.Station;
import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class DeviceTelemetryDayDTO {
    private BigDecimal average;
    private Date day;
    private String key;
    private Station station;
}
