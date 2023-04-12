package com.ifsc.julio.javatcc.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
@Entity
@Table(name = "device_telemetry")
public class DeviceTelemetryEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String key;
    private Date date;
    private Double value;
}
