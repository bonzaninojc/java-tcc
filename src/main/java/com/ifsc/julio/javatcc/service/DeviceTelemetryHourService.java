package com.ifsc.julio.javatcc.service;

import com.ifsc.julio.javatcc.entity.DeviceTelemetryHourEntity;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface DeviceTelemetryHourService {
    void saveAll(List<DeviceTelemetryHourEntity> entities);
    boolean hasPassedThreeHoursSinceLimitDate(UUID station);
    Integer countByDate(Date date, UUID stationId);
}
