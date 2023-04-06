package com.ifsc.julio.javatcc.service;

import com.ifsc.julio.javatcc.entity.DeviceTelemetryDayEntity;
import java.util.List;

public interface DeviceTelemetryDayService {
    void saveAll(List<DeviceTelemetryDayEntity> entities);
}
