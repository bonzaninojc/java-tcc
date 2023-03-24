package com.ifsc.julio.javatcc.dto.query;

import com.ifsc.julio.javatcc.enumeration.TimePeriod;
import lombok.*;
import java.util.Date;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class AverageSearchDTO {
    private String key;
    private Date start;
    private Date end;
    private TimePeriod timePeriod;
}
