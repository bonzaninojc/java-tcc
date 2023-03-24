package com.ifsc.julio.javatcc.service.impl;

import com.ifsc.julio.javatcc.entity.DeviceTelemetryEntity;
import com.ifsc.julio.javatcc.repository.DeviceTelemetryRepository;
import com.ifsc.julio.javatcc.service.DeviceTelemetryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceTelemetryServiceImpl implements DeviceTelemetryService {

    @Autowired
    private DeviceTelemetryRepository deviceTelemetryRepository;

    @Transactional
    @Override
    public void save(DeviceTelemetryEntity entity) {
        deviceTelemetryRepository.save(entity);
    }
}
