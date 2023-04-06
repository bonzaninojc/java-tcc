package com.ifsc.julio.javatcc.controller;

import com.ifsc.julio.javatcc.dto.DeviceSearchDTO;
import com.ifsc.julio.javatcc.entity.DeviceTelemetryEntity;
import com.ifsc.julio.javatcc.rest.ThingsBoardRest;
import com.ifsc.julio.javatcc.service.DeviceTelemetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/things-board")
public class ThingsBoardController {

    @Autowired
    private DeviceTelemetryService deviceTelemetryService;

    @Autowired
    private ThingsBoardRest thingsBoardRest;

    @GetMapping
    public List<DeviceTelemetryEntity> list() {
        return deviceTelemetryService.findAll();
    }

    @PostMapping
    public void teste() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now();
        DeviceSearchDTO deviceSearch = DeviceSearchDTO.builder()
//                .end(end)
//                .start(start)
                .keys(List.of("temperature"))
                .build();
        thingsBoardRest.saveTelemetry(deviceSearch);
    }
}
