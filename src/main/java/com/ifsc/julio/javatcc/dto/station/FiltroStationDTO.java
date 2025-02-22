package com.ifsc.julio.javatcc.dto.station;

import com.ifsc.julio.javatcc.enumeration.Region;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class FiltroStationDTO {
    private Region region;
    private String uf;
    private String ibge;
}
