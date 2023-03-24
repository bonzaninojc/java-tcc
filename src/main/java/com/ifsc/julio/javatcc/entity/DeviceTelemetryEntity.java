package com.ifsc.julio.javatcc.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "device_telemetry")
public class DeviceTelemetryEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String deviceId;
    private String key;
    private Date date;
    private BigDecimal value;
}
