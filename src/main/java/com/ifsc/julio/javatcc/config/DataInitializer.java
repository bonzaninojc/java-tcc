package com.ifsc.julio.javatcc.config;

import com.ifsc.julio.javatcc.dto.AverageDTO;
import com.ifsc.julio.javatcc.dto.DeviceTelemetryDayDTO;
import com.ifsc.julio.javatcc.dto.StationDTO;
import com.ifsc.julio.javatcc.entity.DeviceTelemetryDayEntity;
import com.ifsc.julio.javatcc.entity.DeviceTelemetryEntity;
import com.ifsc.julio.javatcc.entity.DeviceTelemetryHourEntity;
import com.ifsc.julio.javatcc.entity.StationEntity;
import com.ifsc.julio.javatcc.service.DeviceTelemetryDayService;
import com.ifsc.julio.javatcc.service.DeviceTelemetryHourService;
import com.ifsc.julio.javatcc.service.DeviceTelemetryService;
import com.ifsc.julio.javatcc.service.StationService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static com.ifsc.julio.javatcc.util.Const.REQUESTS_DEFAULT;

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
        // Criação de estações
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
                .email("julio.bp25@aluno.ifsc.edu.br")
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
                .email("julio.bp25@aluno.ifsc.edu.br")
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
                .email("julio.bp25@aluno.ifsc.edu.br")
                .phone("48984840861")
                .requestsPerDay(REQUESTS_DEFAULT)
                .nickname("IFSC - Criciúma")
                .ibge("4204608")
                .lat("-28.677405063518073")
                .lng("-49.331830349733025")
                .build();

        stationService.saveAll(List.of(station1, station2, station3, station4));

        UUID stationId1 = stationService.findAll().get(0).getId();
        UUID stationId2 = stationService.findAll().get(1).getId();

        generateTelemetryDataHumidity(stationId1);
        generateTelemetryDataHumidity(stationId2);
        generateTelemetryDataTemperature(stationId1, true);
        generateTelemetryDataTemperature(stationId2, false);
        generateTelemetryDataGas(stationId1);
        generateTelemetryDataGas(stationId2);

        generateTelemetryDataHour(stationId1, "temperature", true);
        generateTelemetryDataHour(stationId2, "temperature", false);
        generateTelemetryDataHour(stationId1, "humidity", true);
        generateTelemetryDataHour(stationId2, "humidity", false);
        generateTelemetryDataHour(stationId1, "CO2", true);
        generateTelemetryDataHour(stationId2, "CO2", false);
    }

    private void generateTelemetryDataHumidity(UUID stationId) {
        List<DeviceTelemetryEntity> telemetryList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        Date now = new Date();
        calendar.setTime(now);
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        StationEntity station = stationService.findById(stationId);

        Random random = new Random();

        while (calendar.getTime().before(now)) {
            Date date = calendar.getTime();

            DeviceTelemetryEntity entity = DeviceTelemetryEntity.builder()
                    .date(date)
                    .value(getValueHumidity(random))
                    .key("humidity")
                    .station(station)
                    .build();

            telemetryList.add(entity);
            calendar.add(Calendar.MINUTE, 10);
        }
        deviceTelemetryService.saveAll(telemetryList);
        dailySchedule(station, "humidity");
    }

    private void generateTelemetryDataTemperature(UUID stationId, boolean isPrimeiraEstacao) {
        List<DeviceTelemetryEntity> telemetryList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        Date now = new Date();
        calendar.setTime(now);
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        StationEntity station = stationService.findById(stationId);

        Random random = new Random();

        while (calendar.getTime().before(now)) {
            Date date = calendar.getTime();

            DeviceTelemetryEntity entity = DeviceTelemetryEntity.builder()
                    .date(date)
                    .value(getValueTemperature(random, isPrimeiraEstacao))
                    .key("temperature")
                    .station(station)
                    .build();

            telemetryList.add(entity);
            calendar.add(Calendar.MINUTE, 10);
        }
        deviceTelemetryService.saveAll(telemetryList);
        dailySchedule(station, "temperature");
    }

    private void generateTelemetryDataGas(UUID stationId) {
        List<DeviceTelemetryEntity> telemetryList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        Date now = new Date();
        calendar.setTime(now);
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        StationEntity station = stationService.findById(stationId);

        Random random = new Random();

        while (calendar.getTime().before(now)) {
            Date date = calendar.getTime();

            DeviceTelemetryEntity entity = DeviceTelemetryEntity.builder()
                    .date(date)
                    .value(getValueGas(random))
                    .key("CO2")
                    .station(station)
                    .build();

            telemetryList.add(entity);
            calendar.add(Calendar.MINUTE, 10);
        }
        deviceTelemetryService.saveAll(telemetryList);
        dailySchedule(station, "CO2");
    }

    private void generateTelemetryDataHour(UUID stationId, String key, boolean isPrimeiraEstacao) {
        List<DeviceTelemetryHourEntity> telemetryHourList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date now = new Date();
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(now);
        calendarEnd.add(Calendar.HOUR_OF_DAY, -1);
        Date endDate = calendarEnd.getTime();

        StationEntity station = stationService.findById(stationId);
        Random random = new Random();

        while (calendar.getTime().before(endDate)) {
            Date date = calendar.getTime();

            Double value = ("temperature".equals(key))
                    ? getValueTemperature(random, isPrimeiraEstacao)
                    : ("humidity".equals(key))
                    ? getValueHumidity(random)
                    : getValueGas(random);

            DeviceTelemetryHourEntity entity = DeviceTelemetryHourEntity.builder()
                    .date(date)
                    .value(value)
                    .key(key)
                    .station(station)
                    .build();

            telemetryHourList.add(entity);
            calendar.add(Calendar.HOUR_OF_DAY, 1);
        }
        deviceTelemetryHourService.saveAll(telemetryHourList);
    }

    private Double getValueTemperature(Random random, boolean isPrimeiraEstacao) {
        if(isPrimeiraEstacao) {
            return (double) (15 + random.nextDouble() * 15);
        }
        return (double) (15 + random.nextDouble() * 5);
    }

    private double getValueGas(Random random) {
        return (double) (60000 + random.nextInt(90001));
    }

    private double getValueHumidity(Random random) {
        return (double) (75 + random.nextInt(16)); // Gera um número entre 75 e 90
    }

    public void dailySchedule(StationEntity station, String key) {
        List<DeviceTelemetryDayDTO> devices = deviceTelemetryService.getDayAverage(getAverageDTO(key));

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

    private AverageDTO getAverageDTO(String key) {
        // Data final: final do dia de ontem (23:59:59.999)
        Calendar finalCal = Calendar.getInstance();
        finalCal.setTime(new Date());
        finalCal.add(Calendar.DATE, -1); // Ontem
        finalCal.set(Calendar.HOUR_OF_DAY, 23);
        finalCal.set(Calendar.MINUTE, 59);
        finalCal.set(Calendar.SECOND, 59);
        finalCal.set(Calendar.MILLISECOND, 999);
        Date finalDate = finalCal.getTime();

        Calendar initCal = Calendar.getInstance();
        initCal.setTime(finalDate);
        initCal.add(Calendar.DATE, -6);
        initCal.set(Calendar.HOUR_OF_DAY, 0);
        initCal.set(Calendar.MINUTE, 0);
        initCal.set(Calendar.SECOND, 0);
        initCal.set(Calendar.MILLISECOND, 0);
        Date initDate = initCal.getTime();

        return AverageDTO.builder()
                .initDate(initDate)
                .finalDate(finalDate)
                .key(key)
                .build();
    }
}
