package com.ifsc.julio.javatcc.schedule;

import com.ifsc.julio.javatcc.dto.AverageDTO;
import com.ifsc.julio.javatcc.dto.DeviceTelemetryHourDTO;
import com.ifsc.julio.javatcc.entity.DeviceTelemetryHourEntity;
import com.ifsc.julio.javatcc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.*;

@EnableScheduling
@Component
public class ThingsBoardHourSchedule {

    @Autowired
    private DeviceTelemetryService deviceTelemetryService;

    @Autowired
    private DeviceTelemetryHourService deviceTelemetryHourService;

    @Scheduled(cron = "5 5 */1 * * *")
    public void hourSchedule() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        calendar.add(Calendar.MINUTE, -30);
        Date finalDate = calendar.getTime();

        calendar.add(Calendar.HOUR, -1);
        Date initDate = calendar.getTime();

        AverageDTO averageDTO = AverageDTO.builder()
                .initDate(initDate)
                .finalDate(finalDate)
                .build();

        List<DeviceTelemetryHourDTO> devices = deviceTelemetryService.getHourAverage(averageDTO);

        List<DeviceTelemetryHourEntity> entities = new ArrayList<>();
        devices.forEach(device -> {
            DeviceTelemetryHourEntity deviceTelemetryHourEntity = DeviceTelemetryHourEntity.builder()
                    .key("temperature")
                    .date(device.getHour())
                    .value(device.getAverage())
                    .build();

            entities.add(deviceTelemetryHourEntity);
        });

        deviceTelemetryHourService.saveAll(entities);
    }
}
