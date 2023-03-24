package com.ifsc.julio.javatcc.dto;

import lombok.*;
import java.math.BigDecimal;
import java.math.BigInteger;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class TelemetryValueDTO {
    private BigInteger ts;
    private BigDecimal value;
}
