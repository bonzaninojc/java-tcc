package com.ifsc.julio.javatcc.service.impl;

import com.ifsc.julio.javatcc.entity.DeviceTelemetryHourEntity;
import com.ifsc.julio.javatcc.repository.DeviceTelemetryHourRepository;
import com.ifsc.julio.javatcc.service.DeviceTelemetryHourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class DeviceTelemetryHourServiceImpl implements DeviceTelemetryHourService {

    @Autowired
    private DeviceTelemetryHourRepository deviceTelemetryHourRepository;

    @Override
    public void saveAll(List<DeviceTelemetryHourEntity> entities) {
        deviceTelemetryHourRepository.saveAll(entities);
    }

    @Override
    public boolean hasPassedThreeHoursSinceLimitDate(UUID station) {
        return deviceTelemetryHourRepository.hasPassedThreeHoursSinceLimitDate(station);
    }

    @Override
    public Integer countByDate(Date date, UUID stationId) {
        return deviceTelemetryHourRepository.countByDate(date, stationId);
    }
}
