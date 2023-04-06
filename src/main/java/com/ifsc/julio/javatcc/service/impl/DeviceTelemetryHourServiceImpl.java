package com.ifsc.julio.javatcc.service.impl;

import com.ifsc.julio.javatcc.entity.DeviceTelemetryHourEntity;
import com.ifsc.julio.javatcc.repository.DeviceTelemetryHourRepository;
import com.ifsc.julio.javatcc.service.DeviceTelemetryHourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DeviceTelemetryHourServiceImpl implements DeviceTelemetryHourService {

    @Autowired
    private DeviceTelemetryHourRepository deviceTelemetryHourRepository;

    @Override
    public void saveAll(List<DeviceTelemetryHourEntity> entities) {
        deviceTelemetryHourRepository.saveAll(entities);
    }
}
