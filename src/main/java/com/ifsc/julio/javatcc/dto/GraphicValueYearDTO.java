package com.ifsc.julio.javatcc.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class GraphicValueYearDTO {
    private String date;
    private String key;
    private Double value;


}
