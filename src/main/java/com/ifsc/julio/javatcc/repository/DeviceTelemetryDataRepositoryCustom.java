package com.ifsc.julio.javatcc.repository;

import com.ifsc.julio.javatcc.dto.GraphicValueDTO;
import com.ifsc.julio.javatcc.dto.GraphicValueFilterDTO;
import com.ifsc.julio.javatcc.dto.GraphicValueYearDTO;

import java.util.List;

public interface DeviceTelemetryDataRepositoryCustom {

    List<GraphicValueDTO> getDataByDay(GraphicValueFilterDTO graphicValueFilterDTO);
    List<GraphicValueDTO> getDataByMonth(GraphicValueFilterDTO graphicValueFilterDTO);

    List<GraphicValueYearDTO> getDataByYear(GraphicValueFilterDTO graphicValueFilterDTO);
}
