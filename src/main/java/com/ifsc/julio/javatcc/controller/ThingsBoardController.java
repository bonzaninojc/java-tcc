package com.ifsc.julio.javatcc.controller;

import com.ifsc.julio.javatcc.dto.AverageDTO;
import com.ifsc.julio.javatcc.dto.DeviceSearchDTO;
import com.ifsc.julio.javatcc.dto.DeviceTelemetryDayDTO;
import com.ifsc.julio.javatcc.entity.DeviceTelemetryDayEntity;
import com.ifsc.julio.javatcc.entity.DeviceTelemetryEntity;
import com.ifsc.julio.javatcc.rest.ThingsBoardRest;
import com.ifsc.julio.javatcc.service.DeviceTelemetryDayService;
import com.ifsc.julio.javatcc.service.DeviceTelemetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static com.ifsc.julio.javatcc.util.Const.*;
import static java.time.temporal.ChronoUnit.*;

@RestController
@RequestMapping("/things-board")
public class ThingsBoardController {

    @Autowired
    private DeviceTelemetryService deviceTelemetryService;

    @Autowired
    private ThingsBoardRest thingsBoardRest;

    @Autowired
    private DeviceTelemetryDayService deviceTelemetryDayService;

    @GetMapping
    public List<DeviceTelemetryEntity> list() {
        return deviceTelemetryService.findAll();
    }

    @PostMapping
    public void teste() {
        LocalDateTime start = LocalDateTime.now().minus(2, YEARS);
        LocalDateTime end = LocalDateTime.now();
        List<String> keys = List.of(TEMPERATURE, HUMIDITY);

        DeviceSearchDTO deviceSearch = DeviceSearchDTO.builder()
                .end(localDateTimeToDate(end))
                .start(localDateTimeToDate(start))
                .keys(keys)
                .build();
        thingsBoardRest.saveTelemetry(deviceSearch);

        List<DeviceTelemetryDayDTO> devices = new ArrayList<>();
        keys.forEach(key -> {
            devices.addAll(deviceTelemetryService.getDayAverage(
                    AverageDTO.builder()
                            .initDate(localDateTimeToDate(start))
                            .finalDate(localDateTimeToDate(end))
                            .key(key)
                            .build()));
        });

        List<DeviceTelemetryDayEntity> entities = new ArrayList<>();
        devices.forEach(device -> {
            DeviceTelemetryDayEntity deviceTelemetryDayEntity = DeviceTelemetryDayEntity.builder()
                    .date(device.getDay())
                    .value(device.getAverage())
                    .key(device.getKey())
                    .build();

            entities.add(deviceTelemetryDayEntity);
        });
        deviceTelemetryDayService.saveAll(entities);
    }

    private Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
