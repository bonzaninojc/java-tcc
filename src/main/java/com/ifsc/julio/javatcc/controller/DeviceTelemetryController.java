package com.ifsc.julio.javatcc.controller;

import com.ifsc.julio.javatcc.dto.GraphicValueDTO;
import com.ifsc.julio.javatcc.dto.GraphicValueFilterDTO;
import com.ifsc.julio.javatcc.dto.GraphicValueYearDTO;
import com.ifsc.julio.javatcc.service.DeviceTelemetryDayService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/device-telemetry")
@Tag(name = "Device Telemetria")
public class DeviceTelemetryController {

    @Autowired
    private DeviceTelemetryDayService deviceTelemetryDayService;

    @PostMapping("find-data-by-day")
    public List<GraphicValueDTO> getDataByDay(@RequestBody GraphicValueFilterDTO graphicValueFilterDTO) {
        return deviceTelemetryDayService.getDataByDay(graphicValueFilterDTO);
    }

    @PostMapping("find-data-by-month")
    public List<GraphicValueDTO> getDataByMonth(@RequestBody GraphicValueFilterDTO graphicValueFilterDTO) {
        return deviceTelemetryDayService.getDataByMonth(graphicValueFilterDTO);
    }

    @PostMapping("find-data-by-year")
    public List<GraphicValueYearDTO> getDataByYear(@RequestBody GraphicValueFilterDTO graphicValueFilterDTO) {
        return deviceTelemetryDayService.getDataByYear(graphicValueFilterDTO);
    }
}
