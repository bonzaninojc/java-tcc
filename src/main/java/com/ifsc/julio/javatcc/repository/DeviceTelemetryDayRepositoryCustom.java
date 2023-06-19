package com.ifsc.julio.javatcc.repository;

import com.ifsc.julio.javatcc.dto.GraphicValueDTO;
import com.ifsc.julio.javatcc.dto.GraphicValueFilterDTO;
import java.util.List;

public interface DeviceTelemetryDayRepositoryCustom {

    List<GraphicValueDTO> getHumidityByDay(GraphicValueFilterDTO graphicValueFilterDTO);
    List<GraphicValueDTO> getHumidityByMonth(GraphicValueFilterDTO graphicValueFilterDTO);
}
