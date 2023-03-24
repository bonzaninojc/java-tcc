package com.ifsc.julio.javatcc.schedule;

import com.ifsc.julio.javatcc.dto.DeviceSearchDTO;
import com.ifsc.julio.javatcc.rest.ThingsBoardRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Date;

@EnableScheduling
@Component
public class ThingsboardDailySchedule {

    @Autowired
    private ThingsBoardRest thingsBoardRest;

    @Scheduled(cron = "0 27 14 * * ?")
    public void myScheduledTask() throws Exception {
        DeviceSearchDTO deviceSearch = DeviceSearchDTO.builder()
                .end(new Date())
                .start(new Date())
                .keys("temperature")
                .build();
        thingsBoardRest.getProperties(deviceSearch);
    }
}
