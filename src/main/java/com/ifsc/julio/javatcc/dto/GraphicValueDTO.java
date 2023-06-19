package com.ifsc.julio.javatcc.dto;

import lombok.*;
import java.util.Date;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class GraphicValueDTO {
    private Date date;
    private Double value;
}
