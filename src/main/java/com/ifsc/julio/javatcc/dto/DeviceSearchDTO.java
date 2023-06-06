package com.ifsc.julio.javatcc.dto;

import lombok.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import static java.lang.String.valueOf;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class DeviceSearchDTO {
    private List<String> keys;
    private Date start;
    private Date end;
    private UUID stationId;

    public String getStartMiliseconds() {
        return valueOf(start.getTime());
    }

    public String getEndMiliseconds() {
        return valueOf(end.getTime());
    }

    public String getKeysString() {
        return String.join(",", keys);
    }
}
