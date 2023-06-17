package com.ifsc.julio.javatcc.config;

import com.ifsc.julio.javatcc.dto.StationDTO;
import com.ifsc.julio.javatcc.service.StationService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;
import static com.ifsc.julio.javatcc.util.Const.*;

@Component
public class DataInitializer {

    @Autowired
    private StationService stationService;

    @PostConstruct
    public void initStation() {
        StationDTO station1 = StationDTO.builder()
                .uf("SC")
                .city("Tubarão")
                .address("Rua Santos Dumont")
                .date(new Date())
                .email("julio.bp25@aluno.ifsc.edu.br")
                .phone("48991455898")
                .requestsPerDay(REQUESTS_DEFAULT)
                .nickname("Casa")
                .codigoIBGE("4218707")
                .lat("-28.487022532987982")
                .lng("-49.011909619973345")
                .build();

        StationDTO station2 = StationDTO.builder()
                .uf("SC")
                .city("Tubarão")
                .address("IFSC")
                .date(new Date())
                .email("vinicius.sb2002@aluno.ifsc.edu.br")
                .phone("48984840861")
                .requestsPerDay(REQUESTS_DEFAULT)
                .nickname("IFSC")
                .codigoIBGE("4218707")
                .lat("-28.474675405574864")
                .lng("-49.0238219011332")
                .build();
        stationService.saveAll(List.of(station1, station2));
    }
}
