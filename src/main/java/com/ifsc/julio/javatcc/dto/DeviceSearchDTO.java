package com.ifsc.julio.javatcc.dto;

import lombok.*;
import java.util.Date;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class DeviceSearchDTO {
    private String keys;
    private Date start;
    private Date end;
}
