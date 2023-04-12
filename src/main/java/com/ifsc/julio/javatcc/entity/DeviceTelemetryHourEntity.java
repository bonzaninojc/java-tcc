package com.ifsc.julio.javatcc.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
@Entity
@Table(name = "device_telemetry_hour")
public class DeviceTelemetryHourEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String key;
    private Date date;
    private Double value;
}
