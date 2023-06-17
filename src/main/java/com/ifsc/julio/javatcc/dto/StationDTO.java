package com.ifsc.julio.javatcc.dto;

import lombok.*;
import java.util.Date;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class StationDTO {
    private UUID id;
    private String uf;
    private String city;
    private String codigoIBGE;
    private String address;
    private Date date;
    private String email;
    private String phone;
    private boolean disabled;
    private Integer requestsPerDay;
    private String nickname;
    private String lat;
    private String lng;
}
