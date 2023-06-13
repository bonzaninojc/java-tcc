package com.ifsc.julio.javatcc.schedule;

import com.ifsc.julio.javatcc.dto.DeviceSearchDTO;
import com.ifsc.julio.javatcc.entity.LostDataEntity;
import com.ifsc.julio.javatcc.entity.StationEntity;
import com.ifsc.julio.javatcc.rest.ThingsBoardRest;
import com.ifsc.julio.javatcc.service.DeviceTelemetryHourService;
import com.ifsc.julio.javatcc.service.LostDataService;
import com.ifsc.julio.javatcc.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;
import static com.ifsc.julio.javatcc.util.Const.*;
import static com.ifsc.julio.javatcc.util.DateUtil.*;

@EnableScheduling
@Component
public class LostDataSchedule {

    @Autowired
    private StationService stationService;

    @Autowired
    private DeviceTelemetryHourService deviceTelemetryHourService;

    @Autowired
    private LostDataService lostDataService;

    @Autowired
    private ThingsBoardRest thingsBoardRest;

    //TODO - Validar hora do agendamento
    @Scheduled(cron = "0 0 0,5 * * *")
    public void lostDataSchedule() {
        List<StationEntity> stations = stationService.findAll();
        Date date = new Date();
        stations.forEach(station -> {
            findLostData(station);
            validateRequests(station, date);
        });
    }

    private void findLostData(StationEntity station) {
        List<LostDataEntity> lostDatas = lostDataService.findByStationId(station.getId());

        lostDatas.forEach(lostData -> {
            DeviceSearchDTO deviceSearch = DeviceSearchDTO.builder()
                    .end(getEndOfDay(lostData.getDate()))
                    .start(getStartOfDay(lostData.getDate()))
                    .keys(KEYS)
                    .stationId(station.getId())
                    .build();

            thingsBoardRest.saveTelemetry(deviceSearch);

            if (isRequestsPerDayValid(station, lostData.getDate())) {
                lostDataService.delete(lostData);
            }
        });
    }

    private void validateRequests(StationEntity station, Date date) {
        if (isRequestsPerDayValid(station, date)) {
            return;
        }
        lostDataService.save(
                LostDataEntity.builder()
                    .date(date)
                    .station(station)
                    .build());
    }

    private boolean isRequestsPerDayValid(StationEntity station, Date date) {
        return station.getRequestsPerDay().equals(deviceTelemetryHourService.countByDate(date, station.getId()));
    }
}
