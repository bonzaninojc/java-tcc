package com.ifsc.julio.javatcc.service;

import com.ifsc.julio.javatcc.entity.DeviceTelemetryHourEntity;
import java.util.List;

public interface DeviceTelemetryHourService {
    void saveAll(List<DeviceTelemetryHourEntity> entities);
}
