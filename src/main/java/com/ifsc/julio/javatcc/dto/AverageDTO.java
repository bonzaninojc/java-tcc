package com.ifsc.julio.javatcc.dto;

import com.ifsc.julio.javatcc.enumeration.Station;
import lombok.*;
import java.util.Date;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class AverageDTO {
    private Date initDate;
    private Date finalDate;
    private String key;
    private Station station;
}
