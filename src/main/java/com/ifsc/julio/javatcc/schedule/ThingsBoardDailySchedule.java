package com.ifsc.julio.javatcc.schedule;

import com.ifsc.julio.javatcc.dto.*;
import com.ifsc.julio.javatcc.entity.DeviceTelemetryDayEntity;
import com.ifsc.julio.javatcc.service.DeviceTelemetryDayService;
import com.ifsc.julio.javatcc.service.DeviceTelemetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.*;

@EnableScheduling
@Component
public class ThingsBoardDailySchedule {

    @Autowired
    private DeviceTelemetryService deviceTelemetryService;

    @Autowired
    private DeviceTelemetryDayService deviceTelemetryDayService;

    @Scheduled(cron = "0 0 0,5 * * *")
    public void dailySchedule() {
        List<DeviceTelemetryDayDTO> devices = deviceTelemetryService.getDayAverage(getAverageDTO());

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

    private AverageDTO getAverageDTO() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        calendar.add(Calendar.MINUTE, -5);
        Date finalDate = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date initDate = calendar.getTime();

        return AverageDTO.builder()
                .initDate(initDate)
                .finalDate(finalDate)
                .build();
    }
}
