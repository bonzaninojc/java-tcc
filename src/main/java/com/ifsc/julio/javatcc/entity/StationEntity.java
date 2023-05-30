package com.ifsc.julio.javatcc.entity;

import com.ifsc.julio.javatcc.dto.StationDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import java.util.Date;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
@Entity
@Table(name = "station")
public class StationEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String uf;
    private String city;
    private String address;
    private Date date;
    private String email;
    private String phone;
    private boolean disabled;

    public void update(StationDTO stationDTO) {
        uf = stationDTO.getUf();
        city = stationDTO.getCity();
        address = stationDTO.getAddress();
        email = stationDTO.getEmail();
        phone = stationDTO.getPhone();
    }
}