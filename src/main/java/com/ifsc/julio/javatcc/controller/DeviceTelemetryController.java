package com.ifsc.julio.javatcc.controller;

import com.ifsc.julio.javatcc.dto.GraphicValueDTO;
import com.ifsc.julio.javatcc.dto.GraphicValueFilterDTO;
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

    @PostMapping("find-humidity-by-day")
    public List<GraphicValueDTO> getHumidityByDay(@RequestBody GraphicValueFilterDTO graphicValueFilterDTO) {
        return deviceTelemetryDayService.getHumidityByDay(graphicValueFilterDTO);
    }

    @PostMapping("find-humidity-by-month")
    public List<GraphicValueDTO> getHumidityByMonth(@RequestBody GraphicValueFilterDTO graphicValueFilterDTO) {
        return deviceTelemetryDayService.getHumidityByMonth(graphicValueFilterDTO);
    }
}
