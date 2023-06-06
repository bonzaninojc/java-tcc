package com.ifsc.julio.javatcc.config;

import com.ifsc.julio.javatcc.dto.AverageDTO;
import com.ifsc.julio.javatcc.dto.DeviceSearchDTO;
import com.ifsc.julio.javatcc.dto.DeviceTelemetryDayDTO;
import com.ifsc.julio.javatcc.dto.StationDTO;
import com.ifsc.julio.javatcc.entity.DeviceTelemetryDayEntity;
import com.ifsc.julio.javatcc.entity.StationEntity;
import com.ifsc.julio.javatcc.rest.ThingsBoardRest;
import com.ifsc.julio.javatcc.service.DeviceTelemetryDayService;
import com.ifsc.julio.javatcc.service.DeviceTelemetryService;
import com.ifsc.julio.javatcc.service.StationService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static com.ifsc.julio.javatcc.util.Const.*;
import static java.time.temporal.ChronoUnit.YEARS;

@Component
public class DataInitializer {

    @Autowired
    private StationService stationService;

    @Autowired
    private DeviceTelemetryService deviceTelemetryService;

    @Autowired
    private ThingsBoardRest thingsBoardRest;

    @Autowired
    private DeviceTelemetryDayService deviceTelemetryDayService;

    private void teste(StationEntity station) {
        LocalDateTime start = LocalDateTime.now().minus(2, YEARS);
        LocalDateTime end = LocalDateTime.now();

        DeviceSearchDTO deviceSearch = DeviceSearchDTO.builder()
                .end(localDateTimeToDate(end))
                .start(localDateTimeToDate(start))
                .keys(KEYS)
                .build();
        thingsBoardRest.saveTelemetry(deviceSearch);

        List<DeviceTelemetryDayDTO> devices = new ArrayList<>();
        KEYS.forEach(key -> {
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
                    .station(station)
                    .build();

            entities.add(deviceTelemetryDayEntity);
        });
        deviceTelemetryDayService.saveAll(entities);
    }

    private Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    @PostConstruct
    public void initStation() {
        StationEntity station = stationService.save(StationDTO.builder()
                .uf("SC")
                .city("Tubarão")
                .address("IFSC")
                .date(new Date())
                .email("julio.bp25@aluno.ifsc.edu.br")
                .phone("48991455898")
                .requestsPerDay(REQUESTS_DEFAULT)
                .build());
        teste(station);
    }
}
