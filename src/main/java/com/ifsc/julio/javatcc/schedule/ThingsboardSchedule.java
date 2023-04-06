package com.ifsc.julio.javatcc.schedule;

import com.ifsc.julio.javatcc.dto.DeviceSearchDTO;
import com.ifsc.julio.javatcc.rest.ThingsBoardRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@EnableScheduling
@Component
public class ThingsboardSchedule {

    @Autowired
    private ThingsBoardRest thingsBoardRest;

    @Scheduled(cron = "0 17 21 * * ?")
    public void myScheduledTask() {
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
