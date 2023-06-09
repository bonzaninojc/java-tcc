package com.ifsc.julio.javatcc.service;

import com.ifsc.julio.javatcc.dto.*;
import com.ifsc.julio.javatcc.entity.DeviceTelemetryEntity;
import java.util.List;

public interface DeviceTelemetryService {
    void saveAll(List<DeviceTelemetryEntity> entities);
    List<DeviceTelemetryEntity> findAll();
    List<DeviceTelemetryHourDTO> getHourAverage(AverageDTO dto);
    List<DeviceTelemetryDayDTO> getDayAverage(AverageDTO dto);
}
