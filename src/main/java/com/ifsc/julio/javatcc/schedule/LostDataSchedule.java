package com.ifsc.julio.javatcc.schedule;

import com.ifsc.julio.javatcc.entity.StationEntity;
import com.ifsc.julio.javatcc.service.DeviceTelemetryHourService;
import com.ifsc.julio.javatcc.service.LostDataService;
import com.ifsc.julio.javatcc.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@EnableScheduling
@Component
public class LostDataSchedule {

    @Autowired
    private StationService stationService;

    @Autowired
    private DeviceTelemetryHourService deviceTelemetryHourService;

    @Autowired
    private LostDataService lostDataService;

    @Scheduled(cron = "0 0 0,5 * * *")
    public void lostDataSchedule() {
        List<StationEntity> stations = stationService.findAll();
        Date limitDate = getLimitDate();
        stations.forEach(station -> validateRequests(station, limitDate));
    }

    private void validateRequests(StationEntity station, Date limitDate) {
        if (station.getRequestsPerDay().equals(deviceTelemetryHourService.countByDate(limitDate))) {
            return;
        }
        //TODO - Validações;
    }

    private Date getLimitDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.getTime();
    }
}
