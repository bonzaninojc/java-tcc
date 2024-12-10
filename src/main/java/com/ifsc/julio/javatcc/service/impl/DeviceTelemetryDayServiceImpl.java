package com.ifsc.julio.javatcc.service.impl;

import com.ifsc.julio.javatcc.dto.GraphicValueDTO;
import com.ifsc.julio.javatcc.dto.GraphicValueFilterDTO;
import com.ifsc.julio.javatcc.dto.GraphicValueYearDTO;
import com.ifsc.julio.javatcc.entity.DeviceTelemetryDayEntity;
import com.ifsc.julio.javatcc.repository.DeviceTelemetryDataRepositoryCustom;
import com.ifsc.julio.javatcc.repository.DeviceTelemetryDayRepository;
import com.ifsc.julio.javatcc.service.DeviceTelemetryDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DeviceTelemetryDayServiceImpl implements DeviceTelemetryDayService {

    @Qualifier("deviceTelemetryDataRepositoryCustomImpl")
    @Autowired
    private DeviceTelemetryDataRepositoryCustom deviceTelemetryDataRepositoryCustom;

    @Autowired
    private DeviceTelemetryDayRepository deviceTelemetryDayRepository;

    @Override
    public void saveAll(List<DeviceTelemetryDayEntity> entities) {
        deviceTelemetryDayRepository.saveAll(entities);
    }

    @Override
    public List<GraphicValueDTO> getDataByDay(GraphicValueFilterDTO graphicValueFilterDTO) {
        return deviceTelemetryDataRepositoryCustom.getDataByDay(graphicValueFilterDTO);
    }

    @Override
    public List<GraphicValueDTO> getDataByMonth(GraphicValueFilterDTO graphicValueDTO) {
        return deviceTelemetryDataRepositoryCustom.getDataByMonth(graphicValueDTO);
    }

    @Override
    public List<GraphicValueYearDTO> getDataByYear(GraphicValueFilterDTO graphicValueFilterDTO) {
        return deviceTelemetryDataRepositoryCustom.getDataByYear(graphicValueFilterDTO);
    }
}
