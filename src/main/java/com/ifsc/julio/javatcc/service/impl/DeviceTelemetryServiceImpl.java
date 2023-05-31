package com.ifsc.julio.javatcc.service.impl;

import com.ifsc.julio.javatcc.dto.*;
import com.ifsc.julio.javatcc.entity.DeviceTelemetryEntity;
import com.ifsc.julio.javatcc.repository.DeviceTelemetryRepository;
import com.ifsc.julio.javatcc.service.DeviceTelemetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DeviceTelemetryServiceImpl implements DeviceTelemetryService {

    @Autowired
    private DeviceTelemetryRepository deviceTelemetryRepository;

    @Override
    public void saveAll(List<DeviceTelemetryEntity> entities) {
        deviceTelemetryRepository.saveAll(entities);
    }

    @Override
    public List<DeviceTelemetryEntity> findAll() {
        return deviceTelemetryRepository.findAll();
    }

    @Override
    public List<DeviceTelemetryHourDTO> getHourAverage(AverageDTO dto) {
        return deviceTelemetryRepository.getHourAverage(dto);
    }

    @Override
    public List<DeviceTelemetryDayDTO> getDayAverage(AverageDTO dto) {
        return deviceTelemetryRepository.getDayAverage(dto);
    }
}
