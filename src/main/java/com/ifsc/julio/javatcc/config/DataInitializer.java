package com.ifsc.julio.javatcc.config;

import com.ifsc.julio.javatcc.dto.*;
import com.ifsc.julio.javatcc.entity.*;
import com.ifsc.julio.javatcc.service.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;
import static com.ifsc.julio.javatcc.util.Const.*;

@Component
public class DataInitializer {

    @Autowired
    private StationService stationService;

    @Autowired
    private DeviceTelemetryHourService deviceTelemetryHourService;

    @Autowired
    private DeviceTelemetryService deviceTelemetryService;

    @Autowired
    private DeviceTelemetryDayService deviceTelemetryDayService;

    @PostConstruct
    public void initStation() {
        StationDTO station1 = StationDTO.builder()
                .uf("SC")
                .city("Araranguá")
                .address("Av. 15 de novembro")
                .date(new Date())
                .email("julio.bp25@aluno.ifsc.edu.br")
                .phone("48991455898")
                .requestsPerDay(REQUESTS_DEFAULT)
                .nickname("IFSC - Araranguá")
                .ibge("4201406")
                .lat("-28.947756838950696")
                .lng("-49.49327910275754")
                .build();

        StationDTO station2 = StationDTO.builder()
                .uf("SC")
                .city("Tubarão")
                .address("Rua Dep. Olices Pedro de Caldas")
                .date(new Date())
                .email("vinicius.sb2002@aluno.ifsc.edu.br")
                .phone("48984840861")
                .requestsPerDay(REQUESTS_DEFAULT)
                .nickname("IFSC - Tubarão")
                .ibge("4218707")
                .lat("-28.474675405574864")
                .lng("-49.0238219011332")
                .build();

        StationDTO station3 = StationDTO.builder()
                .uf("SC")
                .city("Florianópolis")
                .address("Av. Mauro Ramos")
                .date(new Date())
                .email("vinicius.sb2002@aluno.ifsc.edu.br")
                .phone("48984840861")
                .requestsPerDay(REQUESTS_DEFAULT)
                .nickname("IFSC - Florianópolis")
                .ibge("4205407")
                .lat("-27.59420375421378")
                .lng("-48.543223969795434")
                .build();

        StationDTO station4 = StationDTO.builder()
                .uf("SC")
                .city("Criciúma")
                .address("Av. Mauro Ramos")
                .date(new Date())
                .email("vinicius.sb2002@aluno.ifsc.edu.br")
                .phone("48984840861")
                .requestsPerDay(REQUESTS_DEFAULT)
                .nickname("IFSC - Criciúma")
                .ibge("4204608")
                .lat("-28.677405063518073")
                .lng("-49.331830349733025")
                .build();

        stationService.saveAll(List.of(station1, station2, station3, station4));
        generateTelemetryData(stationService.findAll().get(0).getId());
    }

    private void generateTelemetryData(UUID stationId) {
        List<DeviceTelemetryEntity> telemetryList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        Date now = new Date();
        calendar.setTime(now);
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        StationEntity station = stationService.findById(stationId);

        Random random = new Random();

        while (calendar.getTime().before(now)) {
            Date date = calendar.getTime();
            Double value = random.nextDouble() * 50;

            DeviceTelemetryEntity entity = DeviceTelemetryEntity.builder()
                    .date(date)
                    .value(value)
                    .key("humidity")
                    .station(station)
                    .build();

            telemetryList.add(entity);
            calendar.add(Calendar.MINUTE, 10);
        }
        deviceTelemetryService.saveAll(telemetryList);
        dailySchedule(station);
    }

    public void dailySchedule(StationEntity station) {
        List<DeviceTelemetryDayDTO> devices = deviceTelemetryService.getDayAverage(getAverageDTO());

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

    private AverageDTO getAverageDTO() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        Date initDate = calendar.getTime();

        return AverageDTO.builder()
                .initDate(initDate)
                .finalDate(new Date())
                .key("humidity")
                .build();
    }
}
