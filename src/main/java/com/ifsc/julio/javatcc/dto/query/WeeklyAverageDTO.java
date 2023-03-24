package com.ifsc.julio.javatcc.dto.query;

import lombok.*;
import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class WeeklyAverageDTO {
    private Integer week;
    private BigDecimal average;
}
