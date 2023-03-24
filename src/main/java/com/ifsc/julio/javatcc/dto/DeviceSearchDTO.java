package com.ifsc.julio.javatcc.dto;

import lombok.*;
import java.util.Date;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class DeviceSearchDTO {
    private List<String> keys;
    private Date start;
    private Date end;

    public String getKeysString() {
        return String.join(",", keys);
    }
}
