package com.ifsc.julio.javatcc.service;

import com.ifsc.julio.javatcc.dto.GraphicValueDTO;
import com.ifsc.julio.javatcc.dto.GraphicValueFilterDTO;
import com.ifsc.julio.javatcc.entity.DeviceTelemetryDayEntity;
import java.util.List;

public interface DeviceTelemetryDayService {
    void saveAll(List<DeviceTelemetryDayEntity> entities);
    List<GraphicValueDTO> getHumidityByDay(GraphicValueFilterDTO graphicValueFilterDTO);
    List<GraphicValueDTO> getHumidityByMonth(GraphicValueFilterDTO graphicValueFilterDTO);
}
