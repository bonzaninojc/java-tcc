package com.ifsc.julio.javatcc.service.impl;

import com.ifsc.julio.javatcc.dto.GraphicValueDTO;
import com.ifsc.julio.javatcc.dto.GraphicValueFilterDTO;
import com.ifsc.julio.javatcc.entity.DeviceTelemetryDayEntity;
import com.ifsc.julio.javatcc.repository.DeviceTelemetryDayRepository;
import com.ifsc.julio.javatcc.service.DeviceTelemetryDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DeviceTelemetryDayServiceImpl implements DeviceTelemetryDayService {

    @Autowired
    private DeviceTelemetryDayRepository deviceTelemetryDayRepository;

    @Override
    public void saveAll(List<DeviceTelemetryDayEntity> entities) {
        deviceTelemetryDayRepository.saveAll(entities);
    }

    @Override
    public List<GraphicValueDTO> getHumidityByDay(GraphicValueFilterDTO graphicValueFilterDTO) {
        return deviceTelemetryDayRepository.getHumidityByDay(graphicValueFilterDTO);
    }

    @Override
    public List<GraphicValueDTO> getHumidityByMonth(GraphicValueFilterDTO graphicValueDTO) {
        return deviceTelemetryDayRepository.getHumidityByMonth(graphicValueDTO);
    }
}
