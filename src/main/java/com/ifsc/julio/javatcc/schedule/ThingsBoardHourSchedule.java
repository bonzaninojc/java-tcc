package com.ifsc.julio.javatcc.schedule;

import com.ifsc.julio.javatcc.dto.*;
import com.ifsc.julio.javatcc.entity.DeviceTelemetryHourEntity;
import com.ifsc.julio.javatcc.rest.ThingsBoardRest;
import com.ifsc.julio.javatcc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.*;
import static com.ifsc.julio.javatcc.enumeration.Station.TUBARAO;

@EnableScheduling
@Component
public class ThingsBoardHourSchedule {

    @Autowired
    private DeviceTelemetryService deviceTelemetryService;

    @Autowired
    private DeviceTelemetryHourService deviceTelemetryHourService;

    @Autowired
    private ThingsBoardRest thingsBoardRest;

    @Scheduled(cron = "5 5 */1 * * *")
    public void hourSchedule() {
        AverageDTO averageDTO = getAverageDTO();
        DeviceSearchDTO deviceSearch = DeviceSearchDTO.builder()
                .start(averageDTO.getInitDate())
                .end(averageDTO.getFinalDate())
                //TODO - Pegar keys validas
                .keys(List.of("temperature"))
                .build();
        thingsBoardRest.saveTelemetry(deviceSearch);

        List<DeviceTelemetryHourDTO> devices = deviceTelemetryService.getHourAverage(averageDTO);

        List<DeviceTelemetryHourEntity> entities = new ArrayList<>();
        devices.forEach(device -> {
            DeviceTelemetryHourEntity deviceTelemetryHourEntity = DeviceTelemetryHourEntity.builder()
                    .date(device.getHour())
                    .value(device.getAverage())
                    .key(device.getKey())
                    .station(device.getStation())
                    .build();

            entities.add(deviceTelemetryHourEntity);
        });
        deviceTelemetryHourService.saveAll(entities);
    }

    private AverageDTO getAverageDTO() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        calendar.add(Calendar.MINUTE, -30);
        Date finalDate = calendar.getTime();

        calendar.add(Calendar.HOUR, -1);
        Date initDate = calendar.getTime();

        return AverageDTO.builder()
                .initDate(initDate)
                .finalDate(finalDate)
                .key("temperature")
                .station(TUBARAO)
                .build();
    }
}
