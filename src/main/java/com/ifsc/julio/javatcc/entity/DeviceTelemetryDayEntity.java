package com.ifsc.julio.javatcc.entity;

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
@Table(name = "device_telemetry_day")
public class DeviceTelemetryDayEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String key;
    private Date date;
    private Double value;
}
