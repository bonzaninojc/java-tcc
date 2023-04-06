package com.ifsc.julio.javatcc.dto;

import lombok.*;
import java.util.Date;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class AverageDTO {
    private Date initDate;
    private Date finalDate;
}
